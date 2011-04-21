package com.noodlesandwich.streams.implementations;

import com.google.common.base.Function;
import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Stream;

public final class Generator<T> extends Stream<T> {
    private final Function<? super T, ? extends T> iteratingFunction;
    private final T value;

    private boolean fetchedTail = false;
    private Stream<T> tail;

    public Generator(Function<? super T, ? extends T> iteratingFunction, T value) {
        this.iteratingFunction = iteratingFunction;
        this.value = value;
    }

    @Override
    public boolean isNil() {
        return false;
    }

    @Override
    public T head() {
        return value;
    }

    @Override
    public Stream<T> tail() {
        if (!fetchedTail) {
            try {
                tail = new Generator<T>(iteratingFunction, iteratingFunction.apply(value));
            } catch (EndOfStreamException e) {
                tail = Stream.nil();
            }
            fetchedTail = true;
        }

        return tail;
    }
}
