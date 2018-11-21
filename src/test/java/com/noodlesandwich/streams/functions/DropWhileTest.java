package com.noodlesandwich.streams.functions;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;
import com.noodlesandwich.streams.testutils.ThrowingIterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;

public final class DropWhileTest {
    @Test public void
    is_lazy() {
        final Stream<Object> stream = Streams.wrap(new ThrowingIterator());
        stream.dropWhile(null);
    }

    @Test public void
    dropping_while_true_returns_nil() {
        final Stream<Object> stream = Streams.of(new Object(), new Object(), new Object());
        assertThat(stream.dropWhile(ignored -> true), is(nil()));
    }

    @Test public void
    drops_elements_of_a_stream_while_the_predicate_evaluates_to_true() {
        final Stream<Integer> stream = Streams.of(1, 2, 4, 3, 5);
        assertThat(stream.dropWhile(lessThan(4)), contains(4, 3, 5));
    }

    @Test public void
    dropping_while_false_does_nothing() {
        final Stream<Integer> stream = Streams.of(1, 2, 3, 4, 5);
        assertThat(stream.dropWhile(ignored -> false), contains(1, 2, 3, 4, 5));
    }

    @Test public void
    is_repeatable() {
        final Stream<Integer> droppedStream = Streams.of(1, 2, 4, 3, 5).dropWhile(mutable());
        assertThat(droppedStream, contains(3, 5));
        assertThat(droppedStream, contains(3, 5));
    }

    private static Predicate<Integer> lessThan(final int n) {
        return input -> input < n;
    }

    private static Predicate<Object> mutable() {
        return new Predicate<>() {
            private int i = 3;

            @Override
            public boolean test(final Object input) {
                return i-- > 0;
            }
        };
    }
}
