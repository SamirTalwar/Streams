package com.noodlesandwich.streams;

import java.util.Iterator;

import com.noodlesandwich.streams.implementations.Cons;
import com.noodlesandwich.streams.implementations.Nil;
import com.noodlesandwich.streams.implementations.Wrapper;
import com.noodlesandwich.streams.iterators.StreamIterator;

public abstract class Stream<T> implements Iterable<T> {
    public static <T> Stream<T> cons(T head, Stream<T> tail) {
        return new Cons<T>(head, tail);
    }

    public static <T> Stream<T> nil() {
        return new Nil<T>();
    }

    public static <T> Stream<T> from(Iterable<T> iterable) {
        return from(iterable.iterator());
    }

    private static <T> Stream<T> from(Iterator<T> iterator) {
        if (!iterator.hasNext()) {
            return nil();
        }

        return cons(iterator.next(), from(iterator));
    }

    public static <T> Stream<T> wrap(Iterable<T> iterable) {
        return new Wrapper<T>(iterable);
    }

    public abstract boolean isNil();

    public abstract T head();

    public abstract Stream<T> tail();

    @Override
    public Iterator<T> iterator() {
        return new StreamIterator<T>(this);
    }
}
