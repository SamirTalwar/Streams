package com.noodlesandwich.streams.functions;

import java.util.Comparator;

import com.noodlesandwich.streams.Stream;

public class Sort<T> extends Stream<T> {
    private final Stream<T> stream;
    private final Comparator<? super T> comparator;

    private boolean determinedSortedStream = false;
    private Stream<T> sortedStream;

    private final Object lock = new Object();

    public Sort(Comparator<?super T> comparator, Stream<T> stream) {
        this.stream = stream;
        this.comparator = comparator;
    }

    @Override
    public boolean isNil() {
        return stream.isNil();
    }

    @Override
    public T head() {
        return sortedStream().head();
    }

    @Override
    public Stream<T> tail() {
        return sortedStream().tail();
    }

    private Stream<T> sortedStream() {
        synchronized (lock) {
            if (!determinedSortedStream) {
                sortedStream = mergeSort(stream);
                determinedSortedStream = true;
            }
        }

        return sortedStream;
    }

    private Stream<T> mergeSort(Stream<T> stream) {
        final int size = stream.size();
        if (size <= 1) {
            return stream;
        }

        final int middle = size / 2;
        return merge(mergeSort(stream.take(middle)), mergeSort(stream.drop(middle)));
    }

    private Stream<T> merge(Stream<T> left, Stream<T> right) {
        if (left.size() == 0) {
            return right;
        }

        if (right.size() == 0) {
            return left;
        }

        if (comparator.compare(left.head(), right.head()) <= 0) {
            return Stream.cons(left.head(), merge(left.tail(), right));
        } else {
            return Stream.cons(right.head(), merge(left, right.tail()));
        }
    }
}
