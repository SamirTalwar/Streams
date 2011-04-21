package com.noodlesandwich.streams;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.noodlesandwich.streams.functions.All;
import com.noodlesandwich.streams.functions.Any;
import com.noodlesandwich.streams.functions.Concat;
import com.noodlesandwich.streams.functions.ContainmentPredicate;
import com.noodlesandwich.streams.functions.Drop;
import com.noodlesandwich.streams.functions.DropWhile;
import com.noodlesandwich.streams.functions.Filter;
import com.noodlesandwich.streams.functions.Fold;
import com.noodlesandwich.streams.functions.Map;
import com.noodlesandwich.streams.functions.Take;
import com.noodlesandwich.streams.functions.TakeWhile;
import com.noodlesandwich.streams.functions.Unique;
import com.noodlesandwich.streams.functions.Zip;
import com.noodlesandwich.streams.implementations.Cons;
import com.noodlesandwich.streams.implementations.Generator;
import com.noodlesandwich.streams.implementations.Nil;
import com.noodlesandwich.streams.implementations.Wrapper;
import com.noodlesandwich.streams.iterators.StreamIterator;

public abstract class Stream<T> implements Iterable<T> {
    public static <T> Stream<T> cons(T head, Stream<T> tail) {
        return new Cons<T>(head, tail);
    }

    public static <T> Stream<T> nil() {
        return new Nil<T>();
    }

    public static <T> Stream<T> of(T... items) {
        return clone(Iterators.forArray(items));
    }

    public static <T> Stream<T> clone(Iterable<T> iterable) {
        return clone(iterable.iterator());
    }

    private static <T> Stream<T> clone(Iterator<T> iterator) {
        if (!iterator.hasNext()) {
            return nil();
        }

        return cons(iterator.next(), clone(iterator));
    }

    public static <T> Stream<T> wrap(Iterable<T> iterable) {
        return wrap(iterable.iterator());
    }

    public static <T> Stream<T> wrap(Iterator<T> iterator) {
        return new Wrapper<T>(iterator);
    }

    public static <T> Stream<T> generate(Function<? super T, T> iteratingFunction, T start) {
        return new Generator<T>(iteratingFunction, start);
    }

    public static <T> Stream<T> repeat(T value) {
        return generate(Functions.<T>identity(), value);
    }

    public <U> Stream<U> map(Function<? super T, U> function) {
        return new Map<T, U>(function, this);
    }

    public Stream<T> filter(Predicate<? super T> predicate) {
        return new Filter<T>(predicate, this);
    }

    public <U> U fold(FoldFunction<? super T, U> foldFunction, U initializer) {
        return new Fold<T, U>(foldFunction, initializer).apply(this);
    }

    public Stream<T> take(int n) {
        return new Take<T>(n, this);
    }

    public Stream<T> drop(int n) {
        return new Drop<T>(n, this);
    }

    public Stream<T> takeWhile(Predicate<? super T> predicate) {
        return new TakeWhile<T>(predicate, this);
    }

    public Stream<T> dropWhile(Predicate<? super T> predicate) {
        return new DropWhile<T>(predicate, this);
    }

    public Stream<T> concat(Stream<T> nextStream) {
        return new Concat<T>(this, nextStream);
    }

    public <U> Stream<Pair<T, U>> zip(Stream<U> pairedStream) {
        return zipWith(pairedStream, new ZipWithFunction<T, U, Pair<T, U>>() {
            @Override
            public Pair<T, U> apply(T a, U b) {
                return new Pair<T, U>(a, b);
            }
        });
    }

    public <U, V> Stream<V> zipWith(Stream<U> pairedStream, ZipWithFunction<? super T, ? super U, V> zipWithFunction) {
        return new Zip<T, U, V>(zipWithFunction, this, pairedStream);
    }

    public boolean any(Predicate<T> predicate) {
        return new Any<T>(predicate).apply(this);
    }

    public boolean all(Predicate<T> predicate) {
        return new All<T>(predicate).apply(this);
    }

    public boolean contains(T object) {
        return any(Predicates.equalTo(object));
    }

    public Stream<T> unique() {
        return new Unique<T>(this);
    }

    public Stream<T> union(Stream<T> unionedStream) {
        return concat(unionedStream).unique();
    }

    public Stream<T> intersect(final Stream<T> intersectedStream) {
        return unique().filter(new ContainmentPredicate<T>(intersectedStream) {
            @Override
            public boolean apply(T input) {
                return contains(input);
            }
        });
    }

    public Stream<T> except(final Stream<T> exceptedStream) {
        return filter(new ContainmentPredicate<T>(exceptedStream) {
            @Override
            public boolean apply(T input) {
                return !contains(input);
            }
        });
    }

    public Stream<T> symmetricDifference(Stream<T> otherStream) {
        return filter(new ContainmentPredicate<T>(otherStream) {
            @Override
            public boolean apply(T input) {
                return !contains(input);
            }
        }).union(otherStream.filter(new ContainmentPredicate<T>(this) {
            @Override
            public boolean apply(T input) {
                return !contains(input);
            }
        }));
    }

    public int size() {
        return fold(new FoldFunction<T, Integer>() {
            @Override
            public Integer apply(Integer accumulator, T input) {
                return accumulator + 1;
            }
        }, 0);
    }

    public Stream<T> reverse() {
        return fold(new FoldFunction<T, Stream<T>>() {
            @Override
            public Stream<T> apply(Stream<T> accumulator, T input) {
                return cons(input, accumulator);
            }
        }, Stream.<T>nil());
    }

    public List<T> toList() {
        return Lists.newArrayList(this);
    }

    public Set<T> toSet() {
        return Sets.newHashSet(this);
    }

    public <V> java.util.Map<T, V> toMap(Function<? super T, V> valueFunction) {
        java.util.Map<T, V> map = Maps.newHashMap();
        for (T key : toSet()) {
            map.put(key, valueFunction.apply(key));
        }
        return map;
    }

    public T[] toArray(Class<T> type) {
        return Iterables.toArray(this, type);
    }

    public abstract boolean isNil();

    public abstract T head();

    public abstract Stream<T> tail();

    @Override
    public Iterator<T> iterator() {
        return new StreamIterator<T>(this);
    }
}
