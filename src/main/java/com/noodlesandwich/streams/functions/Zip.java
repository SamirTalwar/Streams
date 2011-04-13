package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.Pair;
import com.noodlesandwich.streams.Stream;

public class Zip<F, S> extends Stream<Pair<F, S>> {
    private final Stream<F> first;
    private final Stream<S> second;

    public Zip(Stream<F> first, Stream<S> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean isNil() {
        return first.isNil() || second.isNil();
    }

    @Override
    public Pair<F, S> head() {
        return new Pair<F, S>(first.head(), second.head());
    }

    @Override
    public Stream<Pair<F, S>> tail() {
        return new Zip<F, S>(first.tail(), second.tail());
    }
}
