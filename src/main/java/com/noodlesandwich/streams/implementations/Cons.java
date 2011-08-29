package com.noodlesandwich.streams.implementations;

import com.noodlesandwich.streams.Stream;

public final class Cons<T> extends AbstractStream<T> {
    private final T head;
    private final Stream<T> tail;

    public Cons(final T head, final Stream<T> tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public boolean isEmpty() {
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
