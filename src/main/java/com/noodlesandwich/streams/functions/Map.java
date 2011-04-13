package com.noodlesandwich.streams.functions;

import com.google.common.base.Function;
import com.noodlesandwich.streams.Stream;

public class Map<F, T> extends Stream<T> {
    private final Stream<F> stream;
    private final Function<F, T> function;

    public Map(Function<F, T> function, Stream<F> stream) {
        this.function = function;
        this.stream = stream;
    }

    @Override
    public boolean isNil() {
        return stream.isNil();
    }

    @Override
    public T head() {
        return function.apply(stream.head());
    }

    @Override
    public Stream<T> tail() {
        return new Map<F, T>(function, stream.tail());
    }
}
