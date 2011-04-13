package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Stream;

public class Drop<T> extends Stream<T> {
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
        while (!stream.isNil() && n > 0) {
            stream = stream.tail();
            n--;
        }

        return stream.isNil();
    }

    @Override
    public T head() {
        if (isNil()) {
            throw new EndOfStreamException();
        }

        return stream.head();
    }

    @Override
    public Stream<T> tail() {
        head();
        return stream.tail();
    }
}
