package com.noodlesandwich.streams.implementations;

import java.util.Iterator;

import com.noodlesandwich.streams.CachedStream;
import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Stream;

public final class Wrapper<T> extends CachedStream<T> {
    private final Iterator<T> iterator;

    public Wrapper(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean determineIsNil() {
        return !iterator.hasNext();
    }

    @Override
    public T determineHead() {
        if (isNil()) {
            throw new EndOfStreamException();
        }

        return iterator.next();
    }

    @Override
    public Stream<T> determineTail() {
        head();
        return new Wrapper<T>(iterator);
    }
}
