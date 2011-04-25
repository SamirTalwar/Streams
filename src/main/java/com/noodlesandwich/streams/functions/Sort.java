package com.noodlesandwich.streams.functions;

import java.util.Comparator;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.noodlesandwich.streams.LazyStream;
import com.noodlesandwich.streams.Stream;

public final class Sort<T, U> extends LazyStream<T> {
    private final Stream<T> stream;
    private final Function<? super T, ? extends U> function;
    private final Comparator<? super U> comparator;

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
    protected Stream<T> determineNewStream() {
        return mergeSort(stream);
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
