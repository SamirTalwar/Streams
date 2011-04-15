package com.noodlesandwich.streams.functions;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.noodlesandwich.streams.Stream;

public final class Unique<T> extends Stream<T> {
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
    public boolean isNil() {
        while (!stream.isNil() && before.contains(stream.head())) {
            stream = stream.tail();
        }

        return stream.isNil();
    }

    @Override
    public T head() {
        isNil();

        return stream.head();
    }

    @Override
    public Stream<T> tail() {
        return new Unique<T>(stream.tail(), Sets.union(before, ImmutableSet.of(stream.head())));
    }
}
