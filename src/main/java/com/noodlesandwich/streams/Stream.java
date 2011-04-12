package com.noodlesandwich.streams;

import java.util.Iterator;

import com.noodlesandwich.streams.implementations.Cons;
import com.noodlesandwich.streams.implementations.Nil;
import com.noodlesandwich.streams.implementations.StreamIterator;

public abstract class Stream<T> implements Iterable<T> {
    private static final Stream<Object> NIL = new Nil<Object>();

    public static <T> Stream<T> cons(T head, Stream<T> tail) {
        return new Cons<T>(head, tail);
    }

    @SuppressWarnings("unchecked")
    public static <T> Stream<T> nil() {
        return (Stream<T>) NIL;
    }

    public abstract T head();

    public abstract Stream<T> tail();

    @Override
    public Iterator<T> iterator() {
        return new StreamIterator<T>(this);
    }
}
