package com.demo.common.functions;

/**
 * Function that accepts one argument and produces a result.
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 */
public interface Mapper<T, R> {

    /**
     * This method applies to the given argument.
     *
     * @param t the function argument
     * @return the computed result
     */
    R apply(T t);
}
