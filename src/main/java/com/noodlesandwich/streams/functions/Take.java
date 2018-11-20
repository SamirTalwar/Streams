package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.implementations.CachedStream;

public final class Take<T> extends CachedStream<T> {
    private final int n;
    private final Stream<T> stream;

    public Take(final int n, final Stream<T> stream) {
        if (n < 0) {
            throw new IllegalArgumentException("Cannot take a negative number of elements");
        }

        this.n = n;
        this.stream = stream;
    }

    @Override
    public boolean determineIsEmpty() {
        return n == 0 || stream.isEmpty();
    }

    @Override
    public T determineHead() {
        checkForNil();
        return stream.head();
    }

    @Override
    public Stream<T> determineTail() {
        checkForNil();
        return new Take<>(n - 1, stream.tail());
    }

    private void checkForNil() {
        if (n == 0) {
            throw new EndOfStreamException();
        }
    }
}
