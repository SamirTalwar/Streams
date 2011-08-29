package com.noodlesandwich.streams.implementations;

import com.google.common.base.Function;
import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;

public final class Generator<T> extends AbstractStream<T> {
    private final Function<? super T, ? extends T> iteratingFunction;
    private final T value;

    private boolean fetchedTail = false;
    private Stream<T> tail;

    private final Object lock = new Object();

    public Generator(final Function<? super T, ? extends T> iteratingFunction, final T value) {
        this.iteratingFunction = iteratingFunction;
        this.value = value;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public T head() {
        return value;
    }

    @Override
    public Stream<T> tail() {
        synchronized (lock) {
            if (!fetchedTail) {
                try {
                    tail = new Generator<T>(iteratingFunction, iteratingFunction.apply(value));
                } catch (final EndOfStreamException e) {
                    tail = Streams.nil();
                }
                fetchedTail = true;
            }
        }

        return tail;
    }
}
