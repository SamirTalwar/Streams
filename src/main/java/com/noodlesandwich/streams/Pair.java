package com.noodlesandwich.streams;

import java.util.Objects;

/**
 * A pair of two objects. Intended as the return type for the {@link Stream#zip} method.
 */
public final class Pair<F, S> {
    private final F first;
    private final S second;

    public Pair(final F first, final S second) {
        this.first = first;
        this.second = second;
    }

    public F first() {
        return first;
    }

    public S second() {
        return second;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Pair)) {
            return false;
        }

        @SuppressWarnings("unchecked")
        final Pair<F, S> other = (Pair<F, S>) obj;
        return Objects.equals(first, other.first)
            && Objects.equals(second, other.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", first, second);
    }
}
