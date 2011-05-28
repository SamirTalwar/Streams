package com.noodlesandwich.streams;

/**
 * Designed for use with the {@link Stream#zipWith} method.
 */
public interface ZipWithFunction<A, B, T> {
    T apply(A a, B b);
}
