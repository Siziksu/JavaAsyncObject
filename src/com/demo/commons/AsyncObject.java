package com.demo.commons;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Object used to easily create async calls.
 *
 * @param <O> the result type of the object
 */
public final class AsyncObject<O> {

    private Runnable runnable;
    private boolean executing;
    private Action<O> action;
    private Success<O> success;
    private Error error;

    /**
     * Instantiates an {@code AsyncObject}
     */
    public AsyncObject() {
        // Constructor
    }

    /**
     * Gets if the object is running.
     *
     * @return true if it is running or false if it is not
     */
    public boolean isExecuting() {
        return executing;
    }

    /**
     * Sets the {@link Action} used to create the {@link Runnable} that will
     * be executed in a new {@link Thread} when the method {@link #execute()}
     * is called.
     *
     * @param action the Request that will be used
     * @return {@code AsyncObject}
     */
    public AsyncObject<O> action(final Action<O> action) {
        this.action = action;
        return this;
    }

    /**
     * Sets the {@link Success} used to return the response of the
     * {@link Action} if ends successfully.
     *
     * @param success the Success that will be used
     * @return {@code AsyncObject}
     */
    public AsyncObject<O> success(final Success<O> success) {
        this.success = success;
        return this;
    }

    /**
     * Sets the {@link Error} used to return {@link Exception} that will be
     * thrown if the {@link Action} fails.
     *
     * @param error the Error that will be used
     * @return {@code AsyncObject}
     */
    public AsyncObject<O> error(final Error error) {
        this.error = error;
        return this;
    }

    /**
     * Executes the {@link Action} into a new {@link Thread}.
     */
    public void execute() {
        if (action != null) {
            new Thread(obtainRunnable()).start();
        } else {
            throw new RuntimeException("There is no action to be executed");
        }
    }

    /**
     * Executes the {@link AsyncObject.Action} into a {@link Executor}.
     */
    public void execute(Executor executor) {
        executor.execute(obtainRunnable());
    }

    /**
     * Shutdown the {@link ExecutorService}.
     *
     * @param executor the Executor to be shutdown
     */
    public static void shutdown(ExecutorService executor) {
        executor.shutdown();
    }

    /**
     * Creates a {@link Runnable} using an {@link Action}.
     */
    private Runnable obtainRunnable() {
        if (runnable == null) {
            runnable = new Runnable() {

                @Override
                public void run() {
                    executing = true;
                    try {
                        O response = action.action();
                        if (success != null) {
                            onSuccess(response);
                        } else {
                            System.out.println("Action successfully completed");
                        }
                    } catch (Exception e) {
                        if (error != null) {
                            onError(e);
                        } else {
                            System.out.println(e.toString());
                        }
                    }
                    executing = false;
                    onDone();
                }
            };
        }
        return runnable;
    }

    private void onSuccess(final O response) {
        success.success(response);
    }

    private void onError(final Exception e) {
        error.error(e);
    }

    private void onDone() {
        action.done();
    }

    /**
     * A task that returns a result and may throw an exception.
     * <br>It is designed ot be executed by another thread.
     * <br>It adds two methods used to return the result of the async request:
     * {@code action(O)} and {@code done()}.
     *
     * @param <O> the result type of method {@code request}
     */
    public interface Action<O> {

        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         * @throws Exception if unable to compute a result
         */
        O action() throws Exception;

        /**
         * Emits when the action is done.
         */
        void done();
    }

    /**
     * This task returns a response.
     */
    public interface Success<O> {

        /**
         * This method will be executed if the action ends successfully.
         *
         * @param response the response of the action
         */
        void success(O response);
    }

    /**
     * This task returns an {@link Exception}.
     */
    public interface Error {

        /**
         * This method will be executed if the action fails.
         *
         * @param e the exception returned
         */
        void error(Exception e);
    }
}
