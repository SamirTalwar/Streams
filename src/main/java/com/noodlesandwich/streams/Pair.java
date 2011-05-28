package com.noodlesandwich.streams;

/**
 * A pair of two objects. Intended as the return type for the {@link Stream#zip} method.
 */
public final class Pair<F, S> {
    private final F first;
    private final S second;

    public Pair(F first, S second) {
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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof Pair)) {
            return false;
        }

        @SuppressWarnings("unchecked")
        Pair<F, S> other = (Pair<F, S>) obj;

        if (first == null) {
            if (other.first != null) {
                return false;
            }
        } else if (!first.equals(other.first)) {
            return false;
        }

        if (second == null) {
            if (other.second != null) {
                return false;
            }
        } else if (!second.equals(other.second)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return first.hashCode() ^ second.hashCode();
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", first, second);
    }
}
