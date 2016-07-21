package com.demo.common;

import com.demo.common.functions.Action;
import com.demo.common.functions.Done;
import com.demo.common.functions.Error;
import com.demo.common.functions.Success;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

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
    private Done done;
    private Executor executor;
    private boolean useExecutor;


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
     * be executed in a new {@link Thread} when the method {@link #run()}
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
     */
    public void subscribe(final Success<O> success) {
        this.success = success;
        run();
    }

    /**
     * Sets the {@link Done} used to emit when the response of the
     * {@link Action} if ends.
     *
     * @param done the Done that will be used
     */
    public void subscribe(final Done done) {
        this.done = done;
        run();
    }

    /**
     * Sets the {@link Success} used to return the response of the
     * {@link Action} if ends successfully.
     * <br />
     * Sets the {@link Done} used to emit when the response of the
     * {@link Action} if ends.
     *
     * @param success the Success that will be used
     * @param done    the Done that will be used
     */
    public void subscribe(final Success<O> success, final Done done) {
        this.success = success;
        this.done = done;
        run();
    }

    /**
     * Sets the {@link Success} used to return the response of the
     * {@link Action} if ends successfully.
     * <br />
     * Sets the {@link Error} used to return {@link Exception} that will be
     * thrown if the {@link Action} fails.
     *
     * @param success the Success that will be used
     * @param error   the Error that will be used
     */
    public void subscribe(final Success<O> success, final Error error) {
        this.success = success;
        this.error = error;
        run();
    }

    /**
     * Sets the {@link Success} used to return the response of the
     * {@link Action} if ends successfully.
     * <br />
     * Sets the {@link Error} used to return {@link Exception} that will be
     * thrown if the {@link Action} fails.
     * <br />
     * Sets the {@link Done} used to emit when the response of the
     * {@link Action} if ends.
     *
     * @param success the Success that will be used
     * @param error   the Error that will be used
     * @param done    the Done that will be used
     */
    public void subscribe(final Success<O> success, final Error error, final Done done) {
        this.success = success;
        this.error = error;
        this.done = done;
        run();
    }

    /**
     * Executes the {@link Action} into a new {@link Thread} and gives feedback
     * if any subscription is implemented.
     */
    public void run() {
        if (action != null) {
            if (useExecutor) {
                executor.execute(obtainRunnable());
            } else {
                new Thread(obtainRunnable()).start();
            }
        } else {
            throw new RuntimeException("There is no action to be executed");
        }
    }

    /**
     * Executes the {@link Action} into a {@link Executor}.
     */
    public AsyncObject<O> executeInto(Executor executor) {
        this.executor = executor;
        useExecutor = true;
        return this;
    }

    /**
     * Shutdown the {@link ExecutorService}.
     *
     * @param executor the Executor to be shutdown
     */
    public static void shutdownExecutor(ExecutorService executor) {
        executor.shutdown();
    }

    /**
     * Creates a {@link Runnable} using an {@link Action}.
     */
    private Runnable obtainRunnable() {
        if (runnable == null) {
            runnable = () -> {
                executing = true;
                try {
                    O response = action.action();
                    if (success != null) {
                        success(response);
                    } else {
                        System.out.println("Action successfully completed");
                    }
                } catch (Exception e) {
                    if (error != null) {
                        error(e);
                    } else {
                        System.out.println(e.toString());
                    }
                }
                executing = false;
                if (done != null) {
                    done();
                }
            };
        }
        return runnable;
    }

    private void success(final O response) {
        success.success(response);
    }

    private void error(final Exception e) {
        error.error(e);
    }

    private void done() {
        done.done();
    }
}
