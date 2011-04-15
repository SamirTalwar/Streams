package com.noodlesandwich.streams.functions;

import com.google.common.base.Predicate;
import com.noodlesandwich.streams.Stream;

public final class DropWhile<T> extends Stream<T> {
    private Stream<T> stream;
    private final Predicate<T> predicate;

    public DropWhile(Predicate<T> predicate, Stream<T> stream) {
        this.predicate = predicate;
        this.stream = stream;
    }

    @Override
    public boolean isNil() {
        dropWhilePredicateApplies();
        return stream.isNil();
    }

    @Override
    public T head() {
        dropWhilePredicateApplies();
        return stream.head();
    }

    @Override
    public Stream<T> tail() {
        dropWhilePredicateApplies();
        return stream.tail();
    }

    private void dropWhilePredicateApplies() {
        while (!stream.isNil() && predicate.apply(stream.head())) {
            stream = stream.tail();
        }
    }
}
