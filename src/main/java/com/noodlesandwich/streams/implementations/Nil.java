package com.noodlesandwich.streams.implementations;

import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Stream;

public class Nil<T> extends Stream<T> {
    @Override
    public T first() {
        throw new EndOfStreamException();
    }

    @Override
    public Stream<T> rest() {
        throw new EndOfStreamException();
    }
}
