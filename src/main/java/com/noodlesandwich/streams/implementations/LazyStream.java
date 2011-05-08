package com.noodlesandwich.streams.implementations;

import com.noodlesandwich.streams.Stream;

/**
 * A <code>LazyStream</code> is a stream that must be constructed all at once, instead of simply manipulating values as
 * necessary. It provides a mechanism for this to happen lazily, i.e. only when the stream's values are accessed and not
 * as soon as the stream is constructed.
 */
public abstract class LazyStream<T> extends AbstractStream<T> {
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

    /**
     * Called once when the user attempts to access the values contained in the stream. The result is cached and used
     * for future operations.
     */
    protected abstract Stream<T> determineNewStream();
}
