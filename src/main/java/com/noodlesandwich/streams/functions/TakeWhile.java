package com.noodlesandwich.streams.functions;

import com.google.common.base.Predicate;
import com.noodlesandwich.streams.implementations.CachedStream;
import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Stream;

public final class TakeWhile<T> extends CachedStream<T> {
    private final Stream<T> stream;
    private final Predicate<? super T> predicate;

    public TakeWhile(Predicate<? super T> predicate, Stream<T> stream) {
        this.predicate = predicate;
        this.stream = stream;
    }

    @Override
    public boolean determineIsNil() {
        return stream.isNil() || !predicate.apply(stream.head());
    }

    @Override
    public T determineHead() {
        checkForNil();
        return stream.head();
    }

    @Override
    public Stream<T> determineTail() {
        checkForNil();
        return new TakeWhile<T>(predicate, stream.tail());
    }

    private void checkForNil() {
        if (isNil()) {
            throw new EndOfStreamException();
        }
    }
}
