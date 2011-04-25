package com.noodlesandwich.streams;

import java.util.Comparator;
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
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.noodlesandwich.streams.functions.All;
import com.noodlesandwich.streams.functions.Any;
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
import com.noodlesandwich.streams.implementations.Cons;
import com.noodlesandwich.streams.implementations.Generator;
import com.noodlesandwich.streams.implementations.IteratorWrapper;
import com.noodlesandwich.streams.implementations.Nil;
import com.noodlesandwich.streams.iterators.StreamIterator;

/**
 * <p>A Stream is an immutable structure similar to a linked list in design. Once created, the stream cannot be altered,
 * and any operation performed on it will create a new stream. The only way to access the data in a stream is to
 * iterate over it, either by using a <em>for each</em> loop, or by retrieving the {@link #iterator() iterator}
 * directly, or by using the {@link #head()} or {@link #tail()} methods to access the head or the tail of the stream.
 * In addition, the majority of the operations provided are lazy &mdash; that is, they will not be evaluated until the
 * stream is iterated over or the values within the stream are accessed.</p>
 *
 * <p>Streams are designed to be used with immutable data types. As the stream itself cannot be manipulated, using only
 * immutable types ensures no other code will alter the underlying values within the stream once it has been created,
 * ensuring thread safety.</p>
 *
 * <p>Streams can be created in many ways:</p>
 *
 * <ul>
 *   <li>{@link Stream#nil Stream.nil()} returns an empty stream &mdash; one with no elements.</li>
 *   <li>{@link Stream#cons Stream.cons(T head, Stream&lt;T&gt; tail)} creates a new stream by prepending an element to
 *       an existing stream.</li>
 *   <li>{@link Stream#of Stream.of(a, b, c)} is a shortcut for <code>cons(a, cons(b, cons(c, nil())))</code>.</li>
 *   <li>{@link Stream#clone Stream.clone(Iterable&lt;T&gt;)} will clone an iterable by iterating over it.</li>
 *   <li>{@link Stream#wrap(Iterator) Stream.wrap(Iterator&lt;T&gt;)} wraps a mutable, one-use iterator in an immutable,
 *       many-use stream.</li>
 *   <li>{@link Stream#generate Stream.generate(Function&lt;T, T&gt; function, T start)} generates an infinite stream
 *       using a start value and a function that is applied to each successive result to produce each value in the
 *       stream.<li>
 *   <li>{@link Stream#repeat(Object) Stream.repeat(T value)} creates an infinite stream by repeating the same
 *       value.</li>
 * </ul>
 */
public abstract class Stream<T> implements Iterable<T> {
    /**
     * Creates an empty stream. Attempts to call {@link #head()} or {@link #tail()} on this stream will throw an
     * {@link EndOfStreamException}
     */
    public static <T> Stream<T> nil() {
        return new Nil<T>();
    }

    /**
     * <p>Creates a new stream by prepending <code>head</code> to <code>tail</code>. Can be nested to build up a large
     * stream. It is expected that the final value in the chain will be {@link #nil()}.</p>
     *
     * <strong>Example:</strong>
     *   <code>cons(a, cons(b, cons(c, nil())))</code> will return a stream that when iterated over, will yield
     *   <code>a</code>, then <code>b</code>, then <code>c</code>.
     */
    public static <T> Stream<T> cons(T head, Stream<T> tail) {
        return new Cons<T>(head, tail);
    }

    /**
     * Creates a new stream over the values provided. This is shorthand for nested calls to {@link #cons}.
     */
    public static <T> Stream<T> of(T... items) {
        return clone(Iterators.forArray(items));
    }

    /**
     * Clones an iterable by iterating over it and building up a stream.
     */
    public static <T> Stream<T> clone(Iterable<T> iterable) {
        return clone(iterable.iterator());
    }

    private static <T> Stream<T> clone(Iterator<T> iterator) {
        if (!iterator.hasNext()) {
            return nil();
        }

        return cons(iterator.next(), clone(iterator));
    }

    /**
     * Wraps an iterable. Delegates to {@link #wrap(Iterator) wrap(Iterator&lt;T&gt;)}.
     */
    public static <T> Stream<T> wrap(Iterable<T> iterable) {
        return wrap(iterable.iterator());
    }

    /**
     * Wraps an iterator with a stream that retains values as they are retrieved, ensuring the stream can be iterated
     * over multiple times.
     */
    public static <T> Stream<T> wrap(Iterator<T> iterator) {
        return new IteratorWrapper<T>(iterator);
    }

    /**
     * Generates a stream using a function that is applied to each successive element, starting with the start value
     * provided. This stream will normally be infinite, but the iterating function can terminate it by throwing an
     * {@link EndOfStreamException}.
     */
    public static <T> Stream<T> generate(Function<? super T, ? extends T> iteratingFunction, T start) {
        return new Generator<T>(iteratingFunction, start);
    }

    /**
     * Generates an infinite stream by repeating the value provided.
     */
    public static <T> Stream<T> repeat(T value) {
        return generate(Functions.<T>identity(), value);
    }

    public <U> Stream<U> map(Function<? super T, ? extends U> function) {
        return new Map<T, U>(function, this);
    }

    public Stream<T> filter(Predicate<? super T> predicate) {
        return new Filter<T>(predicate, this);
    }

    public <A> A foldLeft(FoldLeftFunction<A, ? super T> foldFunction, A initializer) {
        A result = initializer;
        for (T value : this) {
            result = foldFunction.apply(result, value);
        }
        return result;
    }

    public <A> A foldRight(FoldRightFunction<? super T, A> foldFunction, A initializer) {
        if (isNil()) {
            return initializer;
        }

        return foldFunction.apply(head(), tail().foldRight(foldFunction, initializer));
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

    public <U, V> Stream<V> zipWith(Stream<U> pairedStream, ZipWithFunction<? super T, ? super U, ? extends V> zipWithFunction) {
        return new Zip<T, U, V>(zipWithFunction, this, pairedStream);
    }

    public boolean any(Predicate<? super T> predicate) {
        return new Any<T>(predicate).apply(this);
    }

    public boolean all(Predicate<? super T> predicate) {
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
        if (isNil()) {
            return 0;
        }

        return 1 + tail().size();
    }

    public Stream<T> reverse() {
        return new Reverse<T>(this);
    }

    @SuppressWarnings("unchecked")
    public Stream<T> sort() {
        return sort((Comparator<? super T>) Ordering.natural());
    }

    public Stream<T> sort(Comparator<? super T> comparator) {
        return new Sort<T, T>(comparator, this);
    }

    public <U extends Comparable<U>> Stream<T> sortBy(Function<? super T, ? extends U> function) {
        return sortBy(function, Ordering.natural());
    }

    public <U> Stream<T> sortBy(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return new Sort<T, U>(function, comparator, this);
    }

    public List<T> toList() {
        return Lists.newArrayList(this);
    }

    public Set<T> toSet() {
        return Sets.newHashSet(this);
    }

    public <V> java.util.Map<T, V> toMap(Function<? super T, ? extends V> valueFunction) {
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
