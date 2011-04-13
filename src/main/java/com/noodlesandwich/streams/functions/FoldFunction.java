package com.noodlesandwich.streams.functions;

public interface FoldFunction<T, A> {
    A apply(A accumulator, T input);
}
