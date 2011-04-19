package com.noodlesandwich.streams.functions;

import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;

public abstract class ContainmentPredicate<T> implements Predicate<T> {
    private final Iterable<T> iterable;
    private Set<T> elements;

    public ContainmentPredicate(Iterable<T> iterable) {
        this.iterable = iterable;
    }

    public boolean contains(T element) {
        if (elements == null) {
            elements = ImmutableSet.copyOf(iterable);
        }

        return elements.contains(element);
    }
}