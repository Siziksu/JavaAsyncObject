package com.demo.commons;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Object used to easily create async calls.
 * <br>It uses a {@link Request} object.
 *
 * @param <O> the result type of the object
 */
public class AsyncObject<O> {

  private static Random rnd = new Random(System.currentTimeMillis());

  private long id;
  private Runnable runnable;
  private Request<O> request;

  /**
   * Instantiates an {@code AsyncObject} and sets the {@link Request}
   * used to create the {@link Runnable} that will be executed in a new {@link Thread}
   * when the method {@link #execute()} is called.
   *
   * @param request the Request that will be used
   */
  public AsyncObject(final Request<O> request) {
    this.request = request;
    this.id = rnd.nextInt(Integer.MAX_VALUE);
  }

  /**
   * Creates a {@link Runnable} using a {@link Request}.
   */
  private Runnable obtainRunnable() {
    if (runnable == null) {
      runnable = new Runnable() {

        @Override
        public void run() {
          try {
            request.onSuccess(id, request.request());
          } catch (Exception e) {
            request.onError(id, e);
          }
        }
      };
    }
    return runnable;
  }

  /**
   * Executes the {@link Request} into a new {@link Thread}.
   */
  public void execute() {
    new Thread(obtainRunnable()).start();
  }

  /**
   * Executes the {@link Request} into a {@link ThreadPoolExecutor}.
   */
  public void execute(ThreadPoolExecutor executor) {
    executor.execute(obtainRunnable());
  }

  /**
   * Executes the {@link Request} into a {@link ExecutorService}.
   */
  public void execute(ExecutorService executor) {
    executor.execute(obtainRunnable());
  }

  /**
   * Shutdown the executor, in this case a {@link ThreadPoolExecutor}.
   *
   * @param executor the Executor to be shutdown
   */
  public static void shutdown(ThreadPoolExecutor executor) {
    executor.shutdown();
  }

  /**
   * Shutdown the executor, in this case a {@link ExecutorService}.
   *
   * @param executor the Executor to be shutdown
   */
  public static void shutdown(ExecutorService executor) {
    executor.shutdown();
  }

  /**
   * Gets the runnable of this {@code AsyncObject}.
   *
   * @return the Runnable of this AsyncObject or null if the Request is not set
   */
  public Runnable getRunnable() {
    return obtainRunnable();
  }

  /**
   * A task that returns a result and may throw an exception.
   * <br>It is designed for classes whose instances are potentially executed by another thread.
   * <br>It adds two methods used to return the result of an async request:
   * {@code onSuccess(O)} and {@code onError(Exception)}.
   *
   * @param <O> the result type of method {@code request}
   */
  public interface Request<O> {

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    O request() throws Exception;

    void onSuccess(long id, O response);

    void onError(long id, Exception e);
  }
}
