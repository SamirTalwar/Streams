package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Stream;

public class Take<T> extends Stream<T> {
    private final int n;
    private final Stream<T> stream;

    public Take(int n, Stream<T> stream) {
        if (n < 0) {
            throw new IllegalArgumentException("Cannot take a negative number of elements");
        }

        this.n = n;
        this.stream = stream;
    }

    @Override
    public boolean isNil() {
        return n == 0;
    }

    @Override
    public T head() {
        if (n == 0) {
            throw new EndOfStreamException();
        }

        return stream.head();
    }

    @Override
    public Stream<T> tail() {
        return new Take<T>(n - 1, stream.tail());
    }
}
