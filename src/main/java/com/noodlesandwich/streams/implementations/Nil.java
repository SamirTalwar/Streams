package com.noodlesandwich.streams.implementations;

import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Stream;

public final class Nil<T> extends AbstractStream<T> {
    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public T head() {
        throw new EndOfStreamException();
    }

    @Override
    public Stream<T> tail() {
        throw new EndOfStreamException();
    }
}
