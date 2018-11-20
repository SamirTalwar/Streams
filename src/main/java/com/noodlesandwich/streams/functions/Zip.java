package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.ZipWithFunction;
import com.noodlesandwich.streams.implementations.CachedStream;

public final class Zip<F, S, R> extends CachedStream<R> {
    private final Stream<F> first;
    private final Stream<S> second;
    private final ZipWithFunction<? super F, ? super S, ? extends R> zipWithFunction;

    public Zip(final ZipWithFunction<? super F, ? super S, ? extends R> zipWithFunction,
               final Stream<F> first,
               final Stream<S> second) {
        this.first = first;
        this.second = second;
        this.zipWithFunction = zipWithFunction;
    }

    @Override
    public boolean determineIsEmpty() {
        return first.isEmpty() || second.isEmpty();
    }

    @Override
    public R determineHead() {
        return zipWithFunction.apply(first.head(), second.head());
    }

    @Override
    public Stream<R> determineTail() {
        return new Zip<>(zipWithFunction, first.tail(), second.tail());
    }
}
