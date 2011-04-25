package com.noodlesandwich.streams.functions;

import java.util.Comparator;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.noodlesandwich.streams.Stream;

public class Sort<T, U> extends Stream<T> {
    private final Stream<T> stream;
    private final Function<? super T, ? extends U> function;
    private final Comparator<? super U> comparator;

    private boolean determinedSortedStream = false;
    private Stream<T> sortedStream;

    private final Object lock = new Object();

    @SuppressWarnings("unchecked")
    public Sort(Comparator<? super U> comparator, Stream<T> stream) {
        this((Function<? super T, ? extends U>) Functions.<T>identity(), comparator, stream);
    }

    public Sort(Function<? super T, ? extends U> function, Comparator<? super U> comparator, Stream<T> stream) {
        this.function = function;
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

        if (comparator.compare(function.apply(left.head()), function.apply(right.head())) <= 0) {
            return Stream.cons(left.head(), merge(left.tail(), right));
        } else {
            return Stream.cons(right.head(), merge(left, right.tail()));
        }
    }
}
