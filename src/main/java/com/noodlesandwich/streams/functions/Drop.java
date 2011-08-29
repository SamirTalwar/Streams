package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.implementations.CachedStream;

public final class Drop<T> extends CachedStream<T> {
    private int n;
    private Stream<T> stream;

    public Drop(final int n, final Stream<T> stream) {
        if (n < 0) {
            throw new IllegalArgumentException("Cannot drop a negative number of elements");
        }

        this.n = n;
        this.stream = stream;
    }

    @Override
    public boolean determineIsEmpty() {
        removeFirstN();
        return stream.isEmpty();
    }

    @Override
    public T determineHead() {
        removeFirstN();
        return stream.head();
    }

    @Override
    public Stream<T> determineTail() {
        removeFirstN();
        return stream.tail();
    }

    private void removeFirstN() {
        while (n > 0 && !stream.isEmpty()) {
            stream = stream.tail();
            --n;
        }
    }
}
