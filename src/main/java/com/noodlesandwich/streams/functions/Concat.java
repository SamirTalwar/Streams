package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.Stream;

public final class Concat<T> extends Stream<T> {
    private final Stream<T> one;
    private final Stream<T> two;

    public Concat(Stream<T> one, Stream<T> two) {
        this.one = one;
        this.two = two;
    }

    @Override
    public boolean isNil() {
        return one.isNil() && two.isNil();
    }

    @Override
    public T head() {
        return !one.isNil() ? one.head() : two.head();
    }

    @Override
    public Stream<T> tail() {
        return !one.isNil() ? new Concat<T>(one.tail(), two) : two.tail();
    }
}
