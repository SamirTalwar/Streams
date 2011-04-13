package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.Stream;

public class Concat<T> extends Stream<T> {
    private final Stream<T> one;
    private final Stream<T> two;

    public static <T> Stream<T> concat(Stream<T> one, Stream<T> two) {
        if (one.isNil()) {
            return two;
        }

        return new Concat<T>(one, two);
    }

    public Concat(Stream<T> one, Stream<T> two) {
        this.one = one;
        this.two = two;
    }

    @Override
    public boolean isNil() {
        return one.isNil();
    }

    @Override
    public T head() {
        return one.head();
    }

    @Override
    public Stream<T> tail() {
        return concat(one.tail(), two);
    }
}
