package com.noodlesandwich.streams.functions;

import java.util.Comparator;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;
import com.noodlesandwich.streams.implementations.LazyStream;

public final class Sort<T, U> extends LazyStream<T> {
    private final Stream<T> stream;
    private final Comparator<? super T> comparator;

    @SuppressWarnings("unchecked")
    public Sort(final Comparator<? super T> comparator, final Stream<T> stream) {
        this.stream = stream;
        this.comparator = comparator;
    }

    @Override
    protected Stream<T> determineNewStream() {
        return mergeSort(stream);
    }

    private Stream<T> mergeSort(final Stream<T> stream) {
        final int size = stream.size();
        if (size <= 1) {
            return stream;
        }

        final int middle = size / 2;
        return merge(mergeSort(stream.take(middle)), mergeSort(stream.drop(middle)));
    }

    private Stream<T> merge(final Stream<T> left, final Stream<T> right) {
        if (left.isEmpty()) {
            return right;
        }

        if (right.isEmpty()) {
            return left;
        }

        if (comparator.compare(left.head(), right.head()) <= 0) {
            return Streams.cons(left.head(), merge(left.tail(), right));
        } else {
            return Streams.cons(right.head(), merge(left, right.tail()));
        }
    }
}
