package com.noodlesandwich.streams;

public interface FoldLeftFunction<T, A> {
    A apply(A accumulator, T input);
}
