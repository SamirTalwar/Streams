package com.noodlesandwich.streams;

public interface FoldLeftFunction<A, T> {
    A apply(A accumulator, T input);
}
