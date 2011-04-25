package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.FoldFunction;
import com.noodlesandwich.streams.LazyStream;
import com.noodlesandwich.streams.Stream;

public class Reverse<T> extends LazyStream<T> {
    private final Stream<T> stream;

    public Reverse(Stream<T> stream) {
        this.stream = stream;
    }

    @Override
    protected Stream<T> determineNewStream() {
        return stream.fold(new FoldFunction<T, Stream<T>>() {
            @Override
            public Stream<T> apply(Stream<T> accumulator, T input) {
                return Stream.cons(input, accumulator);
            }
        }, Stream.<T>nil());
    }
}
