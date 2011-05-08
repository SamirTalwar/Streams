package com.noodlesandwich.streams;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

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
 * <p>The {@link Streams} class provides static methods that can be used to easily construct a Stream.</p>
 **/
public interface Stream<T> extends Iterable<T> {
    /**
     * Transforms all the values in the stream by applying the function to each one and creating a new stream with the
     * results of the function.
     */
    <U> Stream<U> map(Function<? super T, ? extends U> function);

    /**
     * Filters the stream by applying the predicate to each element and removing those for which it returns false.
     */
    Stream<T> filter(Predicate<? super T> predicate);

    /**
     * <p>Reduces the stream to a single object by combining each successive element of the stream with the previous
     * using a function. The first value is combined with the initializer provided.</p>
     *
     * <p><code>foldLeft</code> works over the stream from left to right.</p>
     */
    <A> A foldLeft(FoldLeftFunction<A, ? super T> foldFunction, A initializer);

    /**
     * <p>Reduces the stream to a single object by combining each successive element of the stream with the previous
     * using a function. The first value is combined with the initializer provided.</p>
     *
     * <p><code>foldRight</code> works over the stream from right to left.</p>
     */
    <A> A foldRight(FoldRightFunction<? super T, A> foldFunction, A initializer);

    /**
     * Returns a new stream containing the first <code>n</code> elements of this stream.
     */
    Stream<T> take(int n);

    /**
     * Returns a new stream that skips the first <code>n</code> elements of this stream.
     */
    Stream<T> drop(int n);

    /**
     * Returns a new stream containing the all the elements up to but not including the first element for which the
     * predicate returns false.
     */
    Stream<T> takeWhile(Predicate<? super T> predicate);

    /**
     * Returns a new stream containing the all the elements after and including the first element for which the
     * predicate returns false.
     */
    Stream<T> dropWhile(Predicate<? super T> predicate);

    /**
     * Concatenates two streams.
     */
    Stream<T> concat(Stream<T> nextStream);

    /**
     * <p>Zips one stream with another stream by combining each element in one with the element in the same position of
     * the other. Each element of the new stream will be a {@link com.noodlesandwich.streams.Pair} containing the corresponding elements in the two
     * streams.</p>
     *
     * <p>The stream returned will be the length of the shorter of the two streams. If one is longer than the other, any
     * elements after this will be ignored.</p>
     */
    <U> Stream<Pair<T, U>> zip(Stream<U> pairedStream);

    /**
     * <p>Zips one stream with another stream by combining each element in one with the element in the same position of
     * the other. Each element of the new stream will be the result of applying the function to the corresponding
     * elements in the two streams.</p>
     *
     * <p>The stream returned will be the length of the shorter of the two streams. If one is longer than the other, any
     * elements after this will be ignored.</p>
     */
    <U, V> Stream<V> zipWith(Stream<U> pairedStream, ZipWithFunction<? super T, ? super U, ? extends V> zipWithFunction);

    /**
     * Returns <code>true</code> if any element in the stream matches the predicate; <code>false</code> otherwise. If
     * there are no elements in the list, <code>false</code> is returned.
     */
    boolean any(Predicate<? super T> predicate);

    /**
     * Returns <code>true</code> if all elements in the stream match the predicate; <code>false</code> otherwise. If
     * there are no elements in the list, <code>true</code> is returned.
     */
    boolean all(Predicate<? super T> predicate);

    /**
     * Returns <code>true</code> if the stream contains an element which is equal to the object; <code>false</code>
     * otherwise.
     */
    boolean contains(T object);

    /**
     * Strips all duplicate elements from the stream. <code>unique()</code> will always remove duplicate elements after
     * the first in order to guarantee a stable result.
     */
    Stream<T> unique();

    /**
     * <p>Unions this stream with another. This creates a stream which contains all entries found in either stream.</p>
     *
     * <p><code>union</code> is a set operation, and as such all duplicate entries will be removed in the process.</p>
     */
    Stream<T> union(Stream<T> unionedStream);

    /**
     * <p>Intersects this stream with another. This creates a stream which contains all entries found in both
     * streams.</p>
     *
     * <p><code>intersect</code> is a set operation, and as such all duplicate entries will be removed in the
     * process.</p>
     */
    Stream<T> intersect(Stream<T> intersectedStream);

    /**
     * Removes all entries from the stream which are found in the stream provided.
     */
    Stream<T> except(Stream<T> exceptedStream);

    /**
     * <p>Creates a new stream containing all entries found in only one of this stream and the other stream. Any entries
     * found in both will be discarded.</p>
     *
     * <p><code>symmetricDifference</code> is a set operation, and as such all duplicate entries will be removed in the
     * process.</p>
     */
    Stream<T> symmetricDifference(Stream<T> otherStream);

    /**
     * <p>Calculates the size of the stream.</p>
     *
     * <p><strong>Warning:</strong> As the stream does not know its size, this is potentially an expensive operation, as
     * it can only be found by traversing the entire stream. If the stream is infinite, this will never return.</p>
     */
    int size();

    /**
     * <p>Reverses the stream.</p>
     *
     * <p><strong>Warning:</strong> This is potentially an expensive operation, as it can only be done by traversing the
     * entire stream. If the stream is infinite, operations that attempt to retrieve values from the reversed stream
     * will never return.</p>
     */
    Stream<T> reverse();

    /**
     * <p>Sorts the stream using the natural ordering of the elements. If the type of the stream has no natural
     * ordering (i.e. it does not implement {@link Comparable}), this will throw a {@link ClassCastException}.</p>
     *
     * <p><strong>Warning:</strong> This is potentially an expensive operation, as it can only be done by traversing the
     * entire stream. If the stream is infinite, operations that attempt to retrieve values from the sorted stream will
     * never return.</p>
     */
    Stream<T> sort();

    /**
     * <p>Sorts the stream using a comparator.</p>
     *
     * <p><strong>Warning:</strong> This is potentially an expensive operation, as it can only be done by traversing the
     * entire stream. If the stream is infinite, operations that attempt to retrieve values from the sorted stream will
     * never return.</p>
     */
    Stream<T> sort(Comparator<? super T> comparator);

    /**
     * <p>Sorts the stream using a comparable entity which can be derived from each element.</p>
     *
     * <p><strong>Warning:</strong> This is potentially an expensive operation, as it can only be done by traversing the
     * entire stream. If the stream is infinite, operations that attempt to retrieve values from the sorted stream will
     * never return.</p>
     */
    <U extends Comparable<U>> Stream<T> sortBy(Function<? super T, ? extends U> function);

    /**
     * <p>Sorts the stream using an entity which can be derived from each element and a comparator.</p>
     *
     * <p><strong>Warning:</strong> This is potentially an expensive operation, as it can only be done by traversing the
     * entire stream. If the stream is infinite, operations that attempt to retrieve values from the sorted stream will
     * never return.</p>
     */
    <U> Stream<T> sortBy(Function<? super T, ? extends U> function, Comparator<? super U> comparator);

    /**
     * Converts the stream to an array.
     */
    T[] toArray(Class<T> type);

    /**
     * Converts the stream to a {@link java.util.List}.
     */
    List<T> toList();

    /**
     * Converts the stream to a {@link java.util.Set}.
     */
    Set<T> toSet();

    /**
     * Converts the stream to a {@link java.util.Map Map} using the elements as keys, and a function which derives the
     * value.
     */
    <V> java.util.Map<T, V> toMap(Function<? super T, ? extends V> valueFunction);

    /**
     * Returns <code>true</code> if the stream is nil (has no elements); <code>false</code> otherwise.
     */
    boolean isNil();

    /**
     * Returns the head (the first element) of the stream.
     */
    T head();

    /**
     * Returns the stream that contains the tail (the second element onwards) of the stream.
     */
    Stream<T> tail();

    /**
     * Returns a one-use, forwards-only iterator which can be used to traverse the stream.
     */
    @Override
    Iterator<T> iterator();
}
