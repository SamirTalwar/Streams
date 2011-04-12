package com.noodlesandwich.streams.implementations;

import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Stream;

public final class Nil<T> extends Stream<T> {
    @Override
    public boolean isNil() {
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
