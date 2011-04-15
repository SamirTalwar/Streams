package com.noodlesandwich.streams.functions;

import com.google.common.base.Predicate;
import com.noodlesandwich.streams.Stream;

public final class Filter<T> extends Stream<T> {
    private Stream<T> stream;
    private final Predicate<T> predicate;

    public Filter(Predicate<T> predicate, Stream<T> stream) {
        this.predicate = predicate;
        this.stream = stream;
    }

    @Override
    public boolean isNil() {
        filterNext();
        return stream.isNil();
    }

    @Override
    public T head() {
        filterNext();
        return stream.head();
    }

    @Override
    public Stream<T> tail() {
        filterNext();
        return new Filter<T>(predicate, stream.tail());
    }

    private void filterNext() {
        while (!stream.isNil() && !predicate.apply(stream.head())) {
            stream = stream.tail();
        }
    }
}
