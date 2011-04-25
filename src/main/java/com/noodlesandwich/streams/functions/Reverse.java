package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.CachedStream;
import com.noodlesandwich.streams.FoldFunction;
import com.noodlesandwich.streams.Stream;

public class Reverse<T> extends CachedStream<T> {
    private final Stream<T> stream;

    private boolean determinedReversedStream = false;
    private Stream<T> reversedStream;

    private final Object lock = new Object();

    public Reverse(Stream<T> stream) {
        this.stream = stream;
    }

    @Override
    public boolean determineIsNil() {
        return reversedStream().isNil();
    }

    @Override
    public T determineHead() {
        return reversedStream().head();
    }

    @Override
    public Stream<T> determineTail() {
        return reversedStream().tail();
    }

    public Stream<T> reversedStream() {
        synchronized (lock) {
            if (!determinedReversedStream) {
                reversedStream = stream.fold(new FoldFunction<T, Stream<T>>() {
                    @Override
                    public Stream<T> apply(Stream<T> accumulator, T input) {
                        return Stream.cons(input, accumulator);
                    }
                }, Stream.<T>nil());
                determinedReversedStream = true;
            }
        }

        return reversedStream;
    }
}
