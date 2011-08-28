package com.noodlesandwich.streams.implementations;

import com.google.common.base.Function;
import com.noodlesandwich.streams.Lookup;
import com.noodlesandwich.streams.Stream;

import static com.google.common.base.Predicates.compose;
import static com.google.common.base.Predicates.equalTo;

public final class StreamLookup<K, T> implements Lookup<K, Stream<T>> {
    private final Stream<T> stream;
    private final Function<? super T, ? extends K> keyFunction;

    public StreamLookup(Stream<T> stream, Function<? super T, ? extends K> keyFunction) {
        this.stream = stream;
        this.keyFunction = keyFunction;
    }

    @Override
    public Stream<T> get(K key) {
        return stream.filter(compose(equalTo(key), keyFunction));
    }
}
