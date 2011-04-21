package com.noodlesandwich.streams.functions;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.noodlesandwich.streams.CachedStream;
import com.noodlesandwich.streams.Stream;

public final class Unique<T> extends CachedStream<T> {
    private Stream<T> stream;
    private final Set<T> before;

    public Unique(Stream<T> stream) {
        this(stream, ImmutableSet.<T>of());
    }

    public Unique(Stream<T> stream, Set<T> before) {
        this.stream = stream;
        this.before = before;
    }

    @Override
    public boolean determineIsNil() {
        removeNonUniques();
        return stream.isNil();
    }

    @Override
    public T determineHead() {
        removeNonUniques();
        return stream.head();
    }

    @Override
    public Stream<T> determineTail() {
        return new Unique<T>(stream.tail(), Sets.union(before, ImmutableSet.of(stream.head())));
    }

    private void removeNonUniques() {
        while (!stream.isNil() && before.contains(stream.head())) {
            stream = stream.tail();
        }
    }
}
