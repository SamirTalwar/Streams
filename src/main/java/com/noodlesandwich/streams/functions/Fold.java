package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.Stream;

public class Fold<T, A> {
    private final Stream<T> stream;
    private final FoldFunction<T, A> foldFunction;

    public Fold(FoldFunction<T, A> foldFunction, Stream<T> stream) {
        this.foldFunction = foldFunction;
        this.stream = stream;
    }

    public A apply(A initializer) {
        for (T value : stream) {
            initializer = foldFunction.apply(initializer, value);
        }
        return initializer;
    }
}
