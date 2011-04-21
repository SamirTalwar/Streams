package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.CachedStream;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.ZipWithFunction;

public final class Zip<F, S, R> extends CachedStream<R> {
    private final Stream<F> first;
    private final Stream<S> second;
    private final ZipWithFunction<? super F, ? super S, ? extends R> zipWithFunction;

    public Zip(ZipWithFunction<? super F, ? super S, ? extends R> zipWithFunction, Stream<F> first, Stream<S> second) {
        this.first = first;
        this.second = second;
        this.zipWithFunction = zipWithFunction;
    }

    @Override
    public boolean determineIsNil() {
        return first.isNil() || second.isNil();
    }

    @Override
    public R determineHead() {
        return zipWithFunction.apply(first.head(), second.head());
    }

    @Override
    public Stream<R> determineTail() {
        return new Zip<F, S, R>(zipWithFunction, first.tail(), second.tail());
    }
}
