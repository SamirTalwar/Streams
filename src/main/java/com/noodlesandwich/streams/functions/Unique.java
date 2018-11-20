package com.noodlesandwich.streams.functions;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.implementations.CachedStream;

public final class Unique<T> extends CachedStream<T> {
    private Stream<T> stream;
    private final Set<T> before;

    public Unique(final Stream<T> stream) {
        this(stream, ImmutableSet.of());
    }

    public Unique(final Stream<T> stream, final Set<T> before) {
        this.stream = stream;
        this.before = before;
    }

    @Override
    public boolean determineIsEmpty() {
        removeNonUniques();
        return stream.isEmpty();
    }

    @Override
    public T determineHead() {
        removeNonUniques();
        return stream.head();
    }

    @Override
    public Stream<T> determineTail() {
        return new Unique<>(stream.tail(), Sets.union(before, ImmutableSet.of(stream.head())));
    }

    private void removeNonUniques() {
        while (!stream.isEmpty() && before.contains(stream.head())) {
            stream = stream.tail();
        }
    }
}
