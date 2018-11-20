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
import com.noodlesandwich.streams.FoldLeftFunction;
import com.noodlesandwich.streams.FoldRightFunction;
import com.noodlesandwich.streams.Lookup;
import com.noodlesandwich.streams.Pair;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.ZipWithFunction;
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
    public <U> Stream<U> map(final Function<? super T, ? extends U> function) {
        return new Map<>(function, this);
    }

    @Override
    public Stream<T> filter(final Predicate<? super T> predicate) {
        return new Filter<>(predicate, this);
    }

    @Override
    public <A> A foldLeft(final FoldLeftFunction<A, ? super T> foldFunction, final A initializer) {
        A result = initializer;
        for (final T value : this) {
            result = foldFunction.apply(result, value);
        }
        return result;
    }

    @Override
    public <A> A foldRight(final FoldRightFunction<? super T, A> foldFunction, final A initializer) {
        if (isEmpty()) {
            return initializer;
        }
        return foldFunction.apply(head(), tail().foldRight(foldFunction, initializer));
    }

    @Override
    public Stream<T> take(final int n) {
        return new Take<>(n, this);
    }

    @Override
    public Stream<T> drop(final int n) {
        return new Drop<>(n, this);
    }

    @Override
    public Stream<T> takeWhile(final Predicate<? super T> predicate) {
        return new TakeWhile<>(predicate, this);
    }

    @Override
    public Stream<T> dropWhile(final Predicate<? super T> predicate) {
        return new DropWhile<>(predicate, this);
    }

    @Override
    public Stream<T> concat(final Stream<T> nextStream) {
        return new Concat<>(this, nextStream);
    }

    @Override
    public <U> Stream<Pair<T, U>> zip(final Stream<U> pairedStream) {
        return zipWith(pairedStream, Pair::new);
    }

    @Override
    public <U, V> Stream<V> zipWith(final Stream<U> pairedStream, final ZipWithFunction<? super T, ? super U, ? extends V> zipWithFunction) {
        return new Zip<>(zipWithFunction, this, pairedStream);
    }

    @Override
    public boolean any(final Predicate<? super T> predicate) {
        for (final T value : this) {
            if (predicate.apply(value)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean all(final Predicate<? super T> predicate) {
        for (final T value : this) {
            if (!predicate.apply(value)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean contains(final T object) {
        return any(Predicates.equalTo(object));
    }

    @Override
    public Stream<T> unique() {
        return new Unique<>(this);
    }

    @Override
    public Stream<T> union(final Stream<T> unionedStream) {
        return concat(unionedStream).unique();
    }

    @Override
    public Stream<T> intersect(final Stream<T> intersectedStream) {
        return unique().filter(new ContainmentPredicate<>(intersectedStream));
    }

    @Override
    public Stream<T> except(final Stream<T> exceptedStream) {
        return filter(Predicates.not(new ContainmentPredicate<>(exceptedStream)));
    }

    @Override
    public Stream<T> symmetricDifference(final Stream<T> otherStream) {
        return filter(Predicates.not(new ContainmentPredicate<>(otherStream)))
                .union(otherStream.filter(Predicates.not(new ContainmentPredicate<>(this))));
    }

    @Override
    public int size() {
        if (isEmpty()) {
            return 0;
        }

        return 1 + tail().size();
    }

    @Override
    public Stream<T> reverse() {
        return new Reverse<>(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Stream<T> sort() {
        return sort((Comparator<? super T>) Ordering.natural());
    }

    @Override
    public Stream<T> sort(final Comparator<? super T> comparator) {
        return new Sort<T, T>(comparator, this);
    }

    @Override
    public <U extends Comparable<U>> Stream<T> sortBy(final Function<? super T, ? extends U> function) {
        return sortBy(function, Ordering.natural());
    }

    @Override
    public <U> Stream<T> sortBy(final Function<? super T, ? extends U> function, final Comparator<? super U> comparator) {
        return new Sort<T, U>(function, comparator, this);
    }

    @Override
    public <K> Lookup<K, Stream<T>> groupBy(final Function<? super T, ? extends K> keyFunction) {
        return new StreamLookup<>(this, keyFunction);
    }

    @Override
    public T[] toArray(final Class<T> type) {
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
    public <V> java.util.Map<T, V> toMap(final Function<? super T, ? extends V> valueFunction) {
        final java.util.Map<T, V> map = Maps.newHashMap();
        for (final T key : toSet()) {
            map.put(key, valueFunction.apply(key));
        }
        return map;
    }

    @Override
    public Iterator<T> iterator() {
        return new StreamIterator<>(this);
    }
}
