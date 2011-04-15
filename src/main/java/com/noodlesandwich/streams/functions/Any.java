package com.noodlesandwich.streams.functions;

import com.google.common.base.Predicate;
import com.noodlesandwich.streams.Stream;

public final class Any<T> {
    private final Predicate<T> predicate;

    public Any(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    public boolean apply(Stream<T> stream) {
        for (T value : stream) {
            if (predicate.apply(value)) {
                return true;
            }
        }

        return false;
    }
}
