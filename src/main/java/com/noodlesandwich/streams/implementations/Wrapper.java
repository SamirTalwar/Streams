package com.noodlesandwich.streams.implementations;

import java.util.Iterator;

import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Stream;

public final class Wrapper<T> extends Stream<T> {
    private final Iterator<T> iterator;

    private boolean isNil;
    private boolean fetchedIsNil = false;

    private T head;
    private boolean fetchedHead = false;

    private Stream<T> tail;
    private boolean fetchedTail = false;

    public Wrapper(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean isNil() {
        if (!fetchedIsNil) {
            isNil = !iterator.hasNext();
            fetchedIsNil = true;
        }

        return isNil;
    }

    @Override
    public T head() {
        if (isNil()) {
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
        head();

        if (!fetchedTail) {
            tail = new Wrapper<T>(iterator);
            fetchedTail = true;
        }

        return tail;
    }
}
