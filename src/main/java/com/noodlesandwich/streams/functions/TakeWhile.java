package com.noodlesandwich.streams.functions;

import com.google.common.base.Predicate;
import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Stream;

public final class TakeWhile<T> extends Stream<T> {
    private final Stream<T> stream;
    private final Predicate<T> predicate;

    public TakeWhile(Predicate<T> predicate, Stream<T> stream) {
        this.predicate = predicate;
        this.stream = stream;
    }

    @Override
    public boolean isNil() {
        return stream.isNil() || !predicate.apply(stream.head());
    }

    @Override
    public T head() {
        if (isNil()) {
            throw new EndOfStreamException();
        }

        return stream.head();
    }

    @Override
    public Stream<T> tail() {
        head();

        return new TakeWhile<T>(predicate, stream.tail());
    }
}
