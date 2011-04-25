package com.noodlesandwich.streams;

public interface FoldRightFunction<T, A> {
    A apply(T input, A accumulator);
}
