package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;
import com.noodlesandwich.streams.implementations.LazyStream;

public final class Reverse<T> extends LazyStream<T> {
    private final Stream<T> stream;

    public Reverse(final Stream<T> stream) {
        this.stream = stream;
    }

    @Override
    protected Stream<T> determineNewStream() {
        return stream.foldLeft((accumulator, input) -> Streams.cons(input, accumulator), Streams.nil());
    }
}
