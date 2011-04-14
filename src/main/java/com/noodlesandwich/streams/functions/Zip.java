package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.Stream;

public class Zip<F, S, R> extends Stream<R> {
    private final Stream<F> first;
    private final Stream<S> second;
    private final ZipWithFunction<? super F, ? super S, R> zipWithFunction;

    public Zip(ZipWithFunction<? super F, ? super S, R> zipWithFunction, Stream<F> first, Stream<S> second) {
        this.first = first;
        this.second = second;
        this.zipWithFunction = zipWithFunction;
    }

    @Override
    public boolean isNil() {
        return first.isNil() || second.isNil();
    }

    @Override
    public R head() {
        return zipWithFunction.apply(first.head(), second.head());
    }

    @Override
    public Stream<R> tail() {
        return new Zip<F, S, R>(zipWithFunction, first.tail(), second.tail());
    }
}
