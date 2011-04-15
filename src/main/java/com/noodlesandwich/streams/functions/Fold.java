package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.FoldFunction;
import com.noodlesandwich.streams.Stream;

public final class Fold<T, A> {
    private final FoldFunction<T, A> foldFunction;
    private final A initializer;

    public Fold(FoldFunction<T, A> foldFunction, A initializer) {
        this.foldFunction = foldFunction;
        this.initializer = initializer;
    }

    public A apply(Stream<T> stream) {
        A result = initializer;
        for (T value : stream) {
            result = foldFunction.apply(result, value);
        }
        return result;
    }
}
