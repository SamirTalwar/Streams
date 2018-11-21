package com.noodlesandwich.streams.functions;

import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableSet;

public final class ContainmentPredicate<T> implements Predicate<T> {
    private final Iterable<T> iterable;
    private Set<T> elements;

    private final Object lock = new Object();

    public ContainmentPredicate(final Iterable<T> iterable) {
        this.iterable = iterable;
    }

    @Override
    public boolean test(final T input) {
        synchronized (lock) {
            if (elements == null) {
                elements = ImmutableSet.copyOf(iterable);
            }
        }

        return elements.contains(input);
    }
}
