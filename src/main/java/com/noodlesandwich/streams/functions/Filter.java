package com.noodlesandwich.streams.functions;

import com.google.common.base.Predicate;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.implementations.CachedStream;

public final class Filter<T> extends CachedStream<T> {
    private Stream<T> stream;
    private final Predicate<? super T> predicate;
    private boolean filteredNext = false;

    public Filter(final Predicate<? super T> predicate, final Stream<T> stream) {
        this.predicate = predicate;
        this.stream = stream;
    }

    @Override
    public boolean determineIsEmpty() {
        filterNext();
        return stream.isEmpty();
    }

    @Override
    public T determineHead() {
        filterNext();
        return stream.head();
    }

    @Override
    public Stream<T> determineTail() {
        filterNext();
        return new Filter<>(predicate, stream.tail());
    }

    private void filterNext() {
        if (!filteredNext) {
            while (!stream.isEmpty() && !predicate.apply(stream.head())) {
                stream = stream.tail();
            }
            filteredNext = true;
        }
    }
}
