package com.noodlesandwich.streams.functions;

import com.google.common.base.Predicate;
import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.implementations.CachedStream;

public final class TakeWhile<T> extends CachedStream<T> {
    private final Stream<T> stream;
    private final Predicate<? super T> predicate;

    public TakeWhile(final Predicate<? super T> predicate, final Stream<T> stream) {
        this.predicate = predicate;
        this.stream = stream;
    }

    @Override
    public boolean determineIsEmpty() {
        return stream.isEmpty() || !predicate.apply(stream.head());
    }

    @Override
    public T determineHead() {
        checkForNil();
        return stream.head();
    }

    @Override
    public Stream<T> determineTail() {
        checkForNil();
        return new TakeWhile<>(predicate, stream.tail());
    }

    private void checkForNil() {
        if (isEmpty()) {
            throw new EndOfStreamException();
        }
    }
}
