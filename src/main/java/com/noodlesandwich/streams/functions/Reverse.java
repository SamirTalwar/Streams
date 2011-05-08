package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.Streams;
import com.noodlesandwich.streams.FoldLeftFunction;
import com.noodlesandwich.streams.implementations.LazyStream;
import com.noodlesandwich.streams.Stream;

public final class Reverse<T> extends LazyStream<T> {
    private final Stream<T> stream;

    public Reverse(Stream<T> stream) {
        this.stream = stream;
    }

    @Override
    protected Stream<T> determineNewStream() {
        return stream.foldLeft(new FoldLeftFunction<Stream<T>, T>() {
            @Override
            public Stream<T> apply(Stream<T> accumulator, T input) {
                return Streams.cons(input, accumulator);
            }
        }, Streams.<T>nil());
    }
}
