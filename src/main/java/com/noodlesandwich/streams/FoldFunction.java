package com.noodlesandwich.streams;

public interface FoldFunction<T, A> {
    A apply(A accumulator, T input);
}
