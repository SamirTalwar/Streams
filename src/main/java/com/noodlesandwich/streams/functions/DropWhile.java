package com.noodlesandwich.streams.functions;

import com.google.common.base.Predicate;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.implementations.CachedStream;

public final class DropWhile<T> extends CachedStream<T> {
    private Stream<T> stream;
    private final Predicate<? super T> predicate;

    public DropWhile(final Predicate<? super T> predicate, final Stream<T> stream) {
        this.predicate = predicate;
        this.stream = stream;
    }

    @Override
    public boolean determineIsEmpty() {
        dropWhilePredicateApplies();
        return stream.isEmpty();
    }

    @Override
    public T determineHead() {
        dropWhilePredicateApplies();
        return stream.head();
    }

    @Override
    public Stream<T> determineTail() {
        dropWhilePredicateApplies();
        return stream.tail();
    }

    private void dropWhilePredicateApplies() {
        while (!stream.isEmpty() && predicate.apply(stream.head())) {
            stream = stream.tail();
        }
    }
}
