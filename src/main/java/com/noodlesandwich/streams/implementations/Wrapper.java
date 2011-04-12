package com.noodlesandwich.streams.implementations;

import java.util.Iterator;

import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Stream;

public final class Wrapper<T> extends Stream<T> {
    private final Iterator<T> iterator;
    private final boolean isNil;

    private T head;
    private boolean fetchedHead = false;

    private Stream<T> tail;
    private boolean fetchedTail = false;

    public Wrapper(Iterable<T> iterable) {
        this(iterable.iterator());
    }

    private Wrapper(Iterator<T> iterator) {
        this.iterator = iterator;
        this.isNil = !iterator.hasNext();
    }

    @Override
    public boolean isNil() {
        return isNil;
    }

    @Override
    public T head() {
        if (isNil) {
            throw new EndOfStreamException();
        }

        if (!fetchedHead) {
            head = iterator.next();
            fetchedHead = true;
        }

        return head;
    }

    @Override
    public Stream<T> tail() {
        if (isNil) {
            throw new EndOfStreamException();
        }

        if (!fetchedTail) {
            tail = new Wrapper<T>(iterator);
            fetchedTail = true;
        }

        return tail;
    }
}
