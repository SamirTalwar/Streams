package com.noodlesandwich.streams;

import java.util.Iterator;

import com.noodlesandwich.streams.implementations.Cons;
import com.noodlesandwich.streams.implementations.Nil;
import com.noodlesandwich.streams.implementations.StreamIterator;

public abstract class Stream<T> implements Iterable<T> {
    private static final Stream<Object> NIL = new Nil<Object>();

    public static <T> Stream<T> cons(T first, Stream<T> rest) {
        return new Cons<T>(first, rest);
    }

    @SuppressWarnings("unchecked")
    public static <T> Stream<T> nil() {
        return (Stream<T>) NIL;
    }

    public abstract T first();

    public abstract Stream<T> rest();

    @Override
    public Iterator<T> iterator() {
        return new StreamIterator<T>(this);
    }
}
