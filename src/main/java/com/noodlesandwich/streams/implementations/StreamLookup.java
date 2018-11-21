package com.noodlesandwich.streams.implementations;

import java.util.function.Function;

import com.noodlesandwich.streams.Lookup;
import com.noodlesandwich.streams.Stream;

public final class StreamLookup<K, T> implements Lookup<K, Stream<T>> {
    private final Stream<T> stream;
    private final Function<? super T, ? extends K> keyFunction;

    public StreamLookup(final Stream<T> stream, final Function<? super T, ? extends K> keyFunction) {
        this.stream = stream;
        this.keyFunction = keyFunction;
    }

    @Override
    public Stream<T> get(final K key) {
        return stream.filter(value -> key.equals(keyFunction.apply(value)));
    }
}
