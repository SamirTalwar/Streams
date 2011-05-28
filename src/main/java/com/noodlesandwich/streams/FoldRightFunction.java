package com.noodlesandwich.streams;

/**
 * Designed for use with the {@link Stream#foldRight} method.
 */
public interface FoldRightFunction<T, A> {
    A apply(T input, A accumulator);
}
