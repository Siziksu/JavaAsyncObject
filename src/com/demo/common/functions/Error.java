package com.demo.common.functions;

/**
 * Function that returns an {@link Exception}.
 */
public interface Error {

    /**
     * This method will be executed if the action fails.
     *
     * @param e the exception returned
     */
    void error(Exception e);
}
