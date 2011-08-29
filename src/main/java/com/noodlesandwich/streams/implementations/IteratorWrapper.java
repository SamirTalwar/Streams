package com.noodlesandwich.streams.implementations;

import java.util.Iterator;

import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Stream;

public final class IteratorWrapper<T> extends CachedStream<T> {
    private final Iterator<T> iterator;

    public IteratorWrapper(final Iterator<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean determineIsEmpty() {
        return !iterator.hasNext();
    }

    @Override
    public T determineHead() {
        if (isEmpty()) {
            throw new EndOfStreamException();
        }

        return iterator.next();
    }

    @Override
    public Stream<T> determineTail() {
        head();
        return new IteratorWrapper<T>(iterator);
    }
}
