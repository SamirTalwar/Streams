package com.noodlesandwich.streams.implementations;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.noodlesandwich.streams.*;
import com.noodlesandwich.streams.functions.Concat;
import com.noodlesandwich.streams.functions.ContainmentPredicate;
import com.noodlesandwich.streams.functions.Drop;
import com.noodlesandwich.streams.functions.DropWhile;
import com.noodlesandwich.streams.functions.Filter;
import com.noodlesandwich.streams.functions.Map;
import com.noodlesandwich.streams.functions.Reverse;
import com.noodlesandwich.streams.functions.Sort;
import com.noodlesandwich.streams.functions.Take;
import com.noodlesandwich.streams.functions.TakeWhile;
import com.noodlesandwich.streams.functions.Unique;
import com.noodlesandwich.streams.functions.Zip;
import com.noodlesandwich.streams.iterators.StreamIterator;

/**
 * <p> A base class upon which to build stream implementations.</p>
 */
public abstract class AbstractStream<T> implements Stream<T> {
    @Override
    public <U> Stream<U> map(Function<? super T, ? extends U> function) {
        return new Map<T, U>(function, this);
    }

    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        return new Filter<T>(predicate, this);
    }

    @Override
    public <A> A foldLeft(FoldLeftFunction<A, ? super T> foldFunction, A initializer) {
        A result = initializer;
        for (T value : this) {
            result = foldFunction.apply(result, value);
        }
        return result;
    }

    @Override
    public <A> A foldRight(FoldRightFunction<? super T, A> foldFunction, A initializer) {
        if (isNil()) {
            return initializer;
        }
        return foldFunction.apply(head(), tail().foldRight(foldFunction, initializer));
    }

    @Override
    public Stream<T> take(int n) {
        return new Take<T>(n, this);
    }

    @Override
    public Stream<T> drop(int n) {
        return new Drop<T>(n, this);
    }

    @Override
    public Stream<T> takeWhile(Predicate<? super T> predicate) {
        return new TakeWhile<T>(predicate, this);
    }

    @Override
    public Stream<T> dropWhile(Predicate<? super T> predicate) {
        return new DropWhile<T>(predicate, this);
    }

    @Override
    public Stream<T> concat(Stream<T> nextStream) {
        return new Concat<T>(this, nextStream);
    }

    @Override
    public <U> Stream<Pair<T, U>> zip(Stream<U> pairedStream) {
        return zipWith(pairedStream, new ZipWithFunction<T, U, Pair<T, U>>() {
            @Override
            public Pair<T, U> apply(T a, U b) {
                return new Pair<T, U>(a, b);
            }
        });
    }

    @Override
    public <U, V> Stream<V> zipWith(Stream<U> pairedStream, ZipWithFunction<? super T, ? super U, ? extends V> zipWithFunction) {
        return new Zip<T, U, V>(zipWithFunction, this, pairedStream);
    }

    @Override
    public boolean any(Predicate<? super T> predicate) {
        for (T value : this) {
            if (predicate.apply(value)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean all(Predicate<? super T> predicate) {
        for (T value : this) {
            if (!predicate.apply(value)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean contains(T object) {
        return any(Predicates.equalTo(object));
    }

    @Override
    public Stream<T> unique() {
        return new Unique<T>(this);
    }

    @Override
    public Stream<T> union(Stream<T> unionedStream) {
        return concat(unionedStream).unique();
    }

    @Override
    public Stream<T> intersect(final Stream<T> intersectedStream) {
        return unique().filter(new ContainmentPredicate<T>(intersectedStream));
    }

    @Override
    public Stream<T> except(final Stream<T> exceptedStream) {
        return filter(Predicates.not(new ContainmentPredicate<T>(exceptedStream)));
    }

    @Override
    public Stream<T> symmetricDifference(Stream<T> otherStream) {
        return filter(Predicates.not(new ContainmentPredicate<T>(otherStream)))
                .union(otherStream.filter(Predicates.not(new ContainmentPredicate<T>(this))));
    }

    @Override
    public int size() {
        if (isNil()) {
            return 0;
        }

        return 1 + tail().size();
    }

    @Override
    public Stream<T> reverse() {
        return new Reverse<T>(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Stream<T> sort() {
        return sort((Comparator<? super T>) Ordering.natural());
    }

    @Override
    public Stream<T> sort(Comparator<? super T> comparator) {
        return new Sort<T, T>(comparator, this);
    }

    @Override
    public <U extends Comparable<U>> Stream<T> sortBy(Function<? super T, ? extends U> function) {
        return sortBy(function, Ordering.natural());
    }

    @Override
    public <U> Stream<T> sortBy(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return new Sort<T, U>(function, comparator, this);
    }

    @Override
    public T[] toArray(Class<T> type) {
        return Iterables.toArray(this, type);
    }

    @Override
    public List<T> toList() {
        return Lists.newArrayList(this);
    }

    @Override
    public Set<T> toSet() {
        return Sets.newHashSet(this);
    }

    @Override
    public <V> java.util.Map<T, V> toMap(Function<? super T, ? extends V> valueFunction) {
        java.util.Map<T, V> map = Maps.newHashMap();
        for (T key : toSet()) {
            map.put(key, valueFunction.apply(key));
        }
        return map;
    }

    @Override
    public Iterator<T> iterator() {
        return new StreamIterator<T>(this);
    }
}
