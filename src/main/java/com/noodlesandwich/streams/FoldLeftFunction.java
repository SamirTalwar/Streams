package com.noodlesandwich.streams;

/**
 * Designed for use with the {@link Stream#foldLeft} method.
 */
public interface FoldLeftFunction<A, T> {
    A apply(A accumulator, T input);
}
