package com.noodlesandwich.streams.iterators;

import java.util.Iterator;

import com.noodlesandwich.streams.Stream;

public final class StreamIterator<T> implements Iterator<T> {
    private Stream<T> stream;

    public StreamIterator(final Stream<T> stream) {
        this.stream = stream;
    }

    @Override
    public boolean hasNext() {
        return !stream.isEmpty();
    }

    @Override
    public T next() {
        final T head = stream.head();
        stream = stream.tail();
        return head;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
