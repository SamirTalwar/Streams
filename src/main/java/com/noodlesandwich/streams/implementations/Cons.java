package com.noodlesandwich.streams.implementations;

import com.noodlesandwich.streams.Stream;

public final class Cons<T> extends Stream<T> {
    private final T first;
    private final Stream<T> rest;

    public Cons(T first, Stream<T> rest) {
        this.first = first;
        this.rest = rest;
    }

    @Override
    public T first() {
        return first;
    }

    @Override
    public Stream<T> rest() {
        return rest;
    }
}
