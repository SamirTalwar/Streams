package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.Stream;

public final class Drop<T> extends Stream<T> {
    private int n;
    private Stream<T> stream;

    public Drop(int n, Stream<T> stream) {
        if (n < 0) {
            throw new IllegalArgumentException("Cannot drop a negative number of elements");
        }

        this.n = n;
        this.stream = stream;
    }

    @Override
    public boolean isNil() {
        removeFirstN();
        return stream.isNil();
    }

    @Override
    public T head() {
        removeFirstN();
        return stream.head();
    }

    @Override
    public Stream<T> tail() {
        removeFirstN();
        return stream.tail();
    }

    private void removeFirstN() {
        while (n > 0 && !stream.isNil()) {
            stream = stream.tail();
            n--;
        }
    }
}
