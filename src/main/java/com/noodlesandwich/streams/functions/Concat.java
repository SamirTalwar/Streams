package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.implementations.CachedStream;
import com.noodlesandwich.streams.Stream;

public final class Concat<T> extends CachedStream<T> {
    private final Stream<T> one;
    private final Stream<T> two;

    public Concat(Stream<T> one, Stream<T> two) {
        this.one = one;
        this.two = two;
    }

    @Override
    public boolean determineIsEmpty() {
        return one.isEmpty() && two.isEmpty();
    }

    @Override
    public T determineHead() {
        return !one.isEmpty() ? one.head() : two.head();
    }

    @Override
    public Stream<T> determineTail() {
        return !one.isEmpty() ? new Concat<T>(one.tail(), two) : two.tail();
    }
}
