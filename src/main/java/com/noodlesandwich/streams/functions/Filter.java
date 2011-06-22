package com.noodlesandwich.streams.functions;

import com.google.common.base.Predicate;
import com.noodlesandwich.streams.implementations.CachedStream;
import com.noodlesandwich.streams.Stream;

public final class Filter<T> extends CachedStream<T> {
    private Stream<T> stream;
    private final Predicate<? super T> predicate;
    private boolean filteredNext = false;

    public Filter(Predicate<? super T> predicate, Stream<T> stream) {
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
        return new Filter<T>(predicate, stream.tail());
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
