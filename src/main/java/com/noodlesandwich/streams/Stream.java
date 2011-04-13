package com.noodlesandwich.streams;

import java.util.Iterator;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.noodlesandwich.streams.functions.Concat;
import com.noodlesandwich.streams.functions.Drop;
import com.noodlesandwich.streams.functions.Filter;
import com.noodlesandwich.streams.functions.Fold;
import com.noodlesandwich.streams.functions.FoldFunction;
import com.noodlesandwich.streams.functions.Map;
import com.noodlesandwich.streams.functions.Take;
import com.noodlesandwich.streams.functions.Zip;
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

    public static <T> Stream<T> clone(Iterable<T> iterable) {
        return clone(iterable.iterator());
    }

    private static <T> Stream<T> clone(Iterator<T> iterator) {
        if (!iterator.hasNext()) {
            return nil();
        }

        return cons(iterator.next(), clone(iterator));
    }

    public static <T> Stream<T> wrap(Iterable<T> iterable) {
        return wrap(iterable.iterator());
    }

    public static <T> Stream<T> wrap(Iterator<T> iterator) {
        return new Wrapper<T>(iterator);
    }

    public <U> Stream<U> map(Function<T, U> function) {
        return new Map<T, U>(function, this);
    }

    public Stream<T> filter(Predicate<T> predicate) {
        return new Filter<T>(predicate, this);
    }

    public <U> U fold(FoldFunction<T, U> foldFunction, U initializer) {
        return new Fold<T, U>(foldFunction, initializer).apply(this);
    }

    public Stream<T> concat(Stream<T> nextStream) {
        return Concat.concat(this, nextStream);
    }

    public <U> Stream<Pair<T, U>> zip(Stream<U> pairedStream) {
        return new Zip<T, U>(this, pairedStream);
    }

    public Stream<T> take(int n) {
        return new Take<T>(n, this);
    }

    public Stream<T> drop(int n) {
        return new Drop<T>(n, this);
    }

    public abstract boolean isNil();

    public abstract T head();

    public abstract Stream<T> tail();

    @Override
    public Iterator<T> iterator() {
        return new StreamIterator<T>(this);
    }
}
