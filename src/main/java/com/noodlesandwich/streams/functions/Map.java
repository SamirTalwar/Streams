package com.noodlesandwich.streams.functions;

import com.google.common.base.Function;
import com.noodlesandwich.streams.implementations.CachedStream;
import com.noodlesandwich.streams.Stream;

public final class Map<F, T> extends CachedStream<T> {
    private final Stream<F> stream;
    private final Function<? super F, ? extends T> function;

    public Map(Function<? super F, ? extends T> function, Stream<F> stream) {
        this.function = function;
        this.stream = stream;
    }

    @Override
    public boolean determineIsNil() {
        return stream.isNil();
    }

    @Override
    public T determineHead() {
        return function.apply(stream.head());
    }

    @Override
    public Stream<T> determineTail() {
        return new Map<F, T>(function, stream.tail());
    }
}
