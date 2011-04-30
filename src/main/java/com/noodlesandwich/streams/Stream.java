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
     * <p><strong>Example:</strong></p>
     * <blockquote>
     *   <code>cons(a, cons(b, cons(c, nil())))</code> will return a stream that when iterated over, will yield
     *   <code>a</code>, then <code>b</code>, then <code>c</code>.
     * </blockquote>
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

    /**
     * Transforms all the values in the stream by applying the function to each one and creating a new stream with the
     * results of the function.
     */
    public <U> Stream<U> map(Function<? super T, ? extends U> function) {
        return new Map<T, U>(function, this);
    }

    /**
     * Filters the stream by applying the predicate to each element and removing those for which it returns false.
     */
    public Stream<T> filter(Predicate<? super T> predicate) {
        return new Filter<T>(predicate, this);
    }

    /**
     * <p>Reduces the stream to a single object by combining each successive element of the stream with the previous
     * using a function. The first value is combined with the initializer provided.</p>
     *
     * <p><code>foldLeft</code> works over the stream from left to right.</p>
     */
    public <A> A foldLeft(FoldLeftFunction<A, ? super T> foldFunction, A initializer) {
        A result = initializer;
        for (T value : this) {
            result = foldFunction.apply(result, value);
        }
        return result;
    }

    /**
     * <p>Reduces the stream to a single object by combining each successive element of the stream with the previous
     * using a function. The first value is combined with the initializer provided.</p>
     *
     * <p><code>foldRight</code> works over the stream from right to left.</p>
     */
    public <A> A foldRight(FoldRightFunction<? super T, A> foldFunction, A initializer) {
        if (isNil()) {
            return initializer;
        }

        return foldFunction.apply(head(), tail().foldRight(foldFunction, initializer));
    }

    /**
     * Returns a new stream containing the first <code>n</code> elements of this stream.
     */
    public Stream<T> take(int n) {
        return new Take<T>(n, this);
    }

    /**
     * Returns a new stream that skips the first <code>n</code> elements of this stream.
     */
    public Stream<T> drop(int n) {
        return new Drop<T>(n, this);
    }

    /**
     * Returns a new stream containing the all the elements up to but not including the first element for which the
     * predicate returns false.
     */
    public Stream<T> takeWhile(Predicate<? super T> predicate) {
        return new TakeWhile<T>(predicate, this);
    }

    /**
     * Returns a new stream containing the all the elements after and including the first element for which the
     * predicate returns false.
     */
    public Stream<T> dropWhile(Predicate<? super T> predicate) {
        return new DropWhile<T>(predicate, this);
    }

    /**
     * Concatenates two streams.
     */
    public Stream<T> concat(Stream<T> nextStream) {
        return new Concat<T>(this, nextStream);
    }

    /**
     * <p>Zips one stream with another stream by combining each element in one with the element in the same position of
     * the other. Each element of the new stream will be a {@link Pair} containing the corresponding elements in the two
     * streams.</p>
     *
     * <p>The stream returned will be the length of the shorter of the two streams. If one is longer than the other, any
     * elements after this will be ignored.</p>
     */
    public <U> Stream<Pair<T, U>> zip(Stream<U> pairedStream) {
        return zipWith(pairedStream, new ZipWithFunction<T, U, Pair<T, U>>() {
            @Override
            public Pair<T, U> apply(T a, U b) {
                return new Pair<T, U>(a, b);
            }
        });
    }

    /**
     * <p>Zips one stream with another stream by combining each element in one with the element in the same position of
     * the other. Each element of the new stream will be the result of applying the function to the corresponding
     * elements in the two streams.</p>
     *
     * <p>The stream returned will be the length of the shorter of the two streams. If one is longer than the other, any
     * elements after this will be ignored.</p>
     */
    public <U, V> Stream<V> zipWith(Stream<U> pairedStream, ZipWithFunction<? super T, ? super U, ? extends V> zipWithFunction) {
        return new Zip<T, U, V>(zipWithFunction, this, pairedStream);
    }

    /**
     * Returns <code>true</code> if any element in the stream matches the predicate; <code>false</code> otherwise. If
     * there are no elements in the list, <code>false</code> is returned.
     */
    public boolean any(Predicate<? super T> predicate) {
        for (T value : this) {
            if (predicate.apply(value)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns <code>true</code> if all elements in the stream match the predicate; <code>false</code> otherwise. If
     * there are no elements in the list, <code>true</code> is returned.
     */
    public boolean all(Predicate<? super T> predicate) {
        for (T value : this) {
            if (!predicate.apply(value)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the stream contains an element which is equal to the object; <code>false</code>
     * otherwise.
     */
    public boolean contains(T object) {
        return any(Predicates.equalTo(object));
    }

    /**
     * Strips all duplicate elements from the stream. <code>unique()</code> will always remove duplicate elements after
     * the first in order to guarantee a stable result.
     */
    public Stream<T> unique() {
        return new Unique<T>(this);
    }

    /**
     * <p>Unions this stream with another. This creates a stream which contains all entries found in either stream.</p>
     *
     * <p><code>union</code> is a set operation, and as such all duplicate entries will be removed in the process.</p>
     */
    public Stream<T> union(Stream<T> unionedStream) {
        return concat(unionedStream).unique();
    }

    /**
     * <p>Intersects this stream with another. This creates a stream which contains all entries found in both
     * streams.</p>
     *
     * <p><code>intersect</code> is a set operation, and as such all duplicate entries will be removed in the
     * process.</p>
     */
    public Stream<T> intersect(final Stream<T> intersectedStream) {
        return unique().filter(new ContainmentPredicate<T>(intersectedStream));
    }

    /**
     * Removes all entries from the stream which are found in the stream provided.
     */
    public Stream<T> except(final Stream<T> exceptedStream) {
        return filter(Predicates.not(new ContainmentPredicate<T>(exceptedStream)));
    }

    /**
     * <p>Creates a new stream containing all entries found in only one of this stream and the other stream. Any entries
     * found in both will be discarded.</p>
     *
     * <p><code>symmetricDifference</code> is a set operation, and as such all duplicate entries will be removed in the
     * process.</p>
     */
    public Stream<T> symmetricDifference(Stream<T> otherStream) {
        return filter(Predicates.not(new ContainmentPredicate<T>(otherStream)))
                .union(otherStream.filter(Predicates.not(new ContainmentPredicate<T>(this))));
    }

    /**
     * <p>Calculates the size of the stream.</p>
     *
     * <p><strong>Warning:</strong> As the stream does not know its size, this is potentially an expensive operation, as
     * it can only be found by traversing the entire stream. If the stream is infinite, this will never return.</p>
     */
    public int size() {
        if (isNil()) {
            return 0;
        }

        return 1 + tail().size();
    }

    /**
     * <p>Reverses the stream.</p>
     *
     * <p><strong>Warning:</strong> This is potentially an expensive operation, as it can only be done by traversing the
     * entire stream. If the stream is infinite, operations that attempt to retrieve values from the reversed stream
     * will never return.</p>
     */
    public Stream<T> reverse() {
        return new Reverse<T>(this);
    }

    /**
     * <p>Sorts the stream using the natural ordering of the elements. If the type of the stream has no natural
     * ordering (i.e. it does not implement {@link Comparable}), this will throw a {@link ClassCastException}.</p>
     *
     * <p><strong>Warning:</strong> This is potentially an expensive operation, as it can only be done by traversing the
     * entire stream. If the stream is infinite, operations that attempt to retrieve values from the sorted stream will
     * never return.</p>
     */
    @SuppressWarnings("unchecked")
    public Stream<T> sort() {
        return sort((Comparator<? super T>) Ordering.natural());
    }

    /**
     * <p>Sorts the stream using a comparator.</p>
     *
     * <p><strong>Warning:</strong> This is potentially an expensive operation, as it can only be done by traversing the
     * entire stream. If the stream is infinite, operations that attempt to retrieve values from the sorted stream will
     * never return.</p>
     */
    public Stream<T> sort(Comparator<? super T> comparator) {
        return new Sort<T, T>(comparator, this);
    }

    /**
     * <p>Sorts the stream using a comparable entity which can be derived from each element.</p>
     *
     * <p><strong>Warning:</strong> This is potentially an expensive operation, as it can only be done by traversing the
     * entire stream. If the stream is infinite, operations that attempt to retrieve values from the sorted stream will
     * never return.</p>
     */
    public <U extends Comparable<U>> Stream<T> sortBy(Function<? super T, ? extends U> function) {
        return sortBy(function, Ordering.natural());
    }

    /**
     * <p>Sorts the stream using an entity which can be derived from each element and a comparator.</p>
     *
     * <p><strong>Warning:</strong> This is potentially an expensive operation, as it can only be done by traversing the
     * entire stream. If the stream is infinite, operations that attempt to retrieve values from the sorted stream will
     * never return.</p>
     */
    public <U> Stream<T> sortBy(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return new Sort<T, U>(function, comparator, this);
    }

    /**
     * Converts the stream to an array.
     */
    public T[] toArray(Class<T> type) {
        return Iterables.toArray(this, type);
    }

    /**
     * Converts the stream to a {@link List}.
     */
    public List<T> toList() {
        return Lists.newArrayList(this);
    }

    /**
     * Converts the stream to a {@link Set}.
     */
    public Set<T> toSet() {
        return Sets.newHashSet(this);
    }

    /**
     * Converts the stream to a {@link java.util.Map Map} using the elements as keys, and a function which derives the
     * value.
     */
    public <V> java.util.Map<T, V> toMap(Function<? super T, ? extends V> valueFunction) {
        java.util.Map<T, V> map = Maps.newHashMap();
        for (T key : toSet()) {
            map.put(key, valueFunction.apply(key));
        }
        return map;
    }

    /**
     * Returns <code>true</code> if the stream is nil (has no elements); <code>false</code> otherwise.
     */
    public abstract boolean isNil();

    /**
     * Returns the head (the first element) of the stream.
     */
    public abstract T head();

    /**
     * Returns the stream that contains the tail (the second element onwards) of the stream.
     */
    public abstract Stream<T> tail();

    /**
     * Returns a one-use, forwards-only iterator which can be used to traverse the stream.
     */
    @Override
    public Iterator<T> iterator() {
        return new StreamIterator<T>(this);
    }
}
