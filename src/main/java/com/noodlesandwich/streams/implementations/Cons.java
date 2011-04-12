package com.noodlesandwich.streams.implementations;

import com.noodlesandwich.streams.Stream;

public final class Cons<T> extends Stream<T> {
    private final T head;
    private final Stream<T> tail;

    public Cons(T head, Stream<T> tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public boolean isNil() {
        return false;
    }

    @Override
    public T head() {
        return head;
    }

    @Override
    public Stream<T> tail() {
        return tail;
    }
}
