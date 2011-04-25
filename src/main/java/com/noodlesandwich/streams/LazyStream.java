package com.noodlesandwich.streams;

public abstract class LazyStream<T> extends Stream<T> {
    private boolean determinedNewStream = false;
    private Stream<T> newStream;

    private final Object lock = new Object();

    @Override
    public boolean isNil() {
        return newStream().isNil();
    }

    @Override
    public T head() {
        return newStream().head();
    }

    @Override
    public Stream<T> tail() {
        return newStream().tail();
    }

    private Stream<T> newStream() {
        synchronized (lock) {
            if (!determinedNewStream) {
                newStream = determineNewStream();
                determinedNewStream = true;
            }
        }

        return newStream;
    }

    protected abstract Stream<T> determineNewStream();
}
