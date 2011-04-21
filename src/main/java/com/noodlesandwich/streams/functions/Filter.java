package com.noodlesandwich.streams.functions;

import com.google.common.base.Predicate;
import com.noodlesandwich.streams.CachedStream;
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
    public boolean determineIsNil() {
        filterNext();
        return stream.isNil();
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
            while (!stream.isNil() && !predicate.apply(stream.head())) {
                stream = stream.tail();
            }
            filteredNext = true;
        }
    }
}
