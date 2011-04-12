package com.noodlesandwich.streams.implementations;

import java.util.Iterator;

import com.noodlesandwich.streams.Stream;

public final class StreamIterator<T> implements Iterator<T> {
    private Stream<T> stream;

    public StreamIterator(Stream<T> stream) {
        this.stream = stream;
    }

    @Override
    public boolean hasNext() {
        return stream == Stream.nil();
    }

    @Override
    public T next() {
        T first = stream.first();
        stream = stream.rest();
        return first;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
