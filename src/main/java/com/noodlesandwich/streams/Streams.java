package com.noodlesandwich.streams;

import java.util.Iterator;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Iterators;
import com.noodlesandwich.streams.implementations.Cons;
import com.noodlesandwich.streams.implementations.Generator;
import com.noodlesandwich.streams.implementations.IteratorWrapper;
import com.noodlesandwich.streams.implementations.Nil;

/**
 * <p>Mechanisms for constructing streams.<p>
 *
 * <ul>
 *   <li>{@link #nil Streams.nil()} returns an empty stream &mdash; one with no elements.</li>
 *   <li>{@link #cons Streams.cons(T head, Stream&lt;T&gt; tail)} creates a new stream by prepending an element to
 *       an existing stream.</li>
 *   <li>{@link #of Streams.of(a, b, c)} is a shortcut for <code>cons(a, cons(b, cons(c, nil())))</code>.</li>
 *   <li>{@link #clone Streams.clone(Iterable&lt;T&gt;)} will clone an iterable by iterating over it.</li>
 *   <li>{@link #wrap(Iterator) Stream.wrap(Iterator&lt;T&gt;)} wraps a mutable, one-use iterator in an immutable,
 *       many-use stream.</li>
 *   <li>{@link #generate Streams.generate(Function&lt;T, T&gt; function, T start)} generates an infinite stream
 *       using a start value and a function that is applied to each successive result to produce each value in the
 *       stream.</li>
 *   <li>{@link #repeat(Object) Streams.repeat(T value)} creates an infinite stream by repeating the same
 *       value.</li>
 * </ul>
 */
public final class Streams {
    private Streams() { }

    /**
     * Creates an empty stream. Attempts to call {@link Stream#head()} or {@link Stream#tail()} on this stream will
     * throw an {@link EndOfStreamException}.
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

    public static <T> Stream<T> clone(Iterator<T> iterator) {
        if (!iterator.hasNext()) {
            return nil();
        }

        return cons(iterator.next(), clone(iterator));
    }

    /**
     * Wraps an iterable. Delegates to {@link #wrap(java.util.Iterator) wrap(Iterator&lt;T&gt;)}.
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
}
