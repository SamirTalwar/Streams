package com.noodlesandwich.streams.functions;

import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;
import com.noodlesandwich.streams.testutils.ThrowingIterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;

public final class TakeWhileTest {
    @Test public void
    is_lazy() {
        final Stream<Object> stream = Streams.wrap(new ThrowingIterator());
        stream.takeWhile(null);
    }

    @Test public void
    taking_while_false_returns_nil() {
        final Stream<Object> stream = Streams.of(new Object(), new Object(), new Object());
        assertThat(stream.takeWhile(ignored -> false), is(nil()));
    }

    @Test public void
    takes_elements_of_a_stream_while_the_predicate_evaluates_to_true() {
        final Stream<Integer> stream = Streams.of(1, 2, 4, 3, 5);
        assertThat(stream.takeWhile(lessThan(4)), contains(1, 2));
    }

    @Test public void
    taking_while_true_does_nothing() {
        final Stream<Integer> stream = Streams.of(1, 2, 3, 4, 5);
        assertThat(stream.takeWhile(ignored -> true), contains(1, 2, 3, 4, 5));
    }

    @Test public void
    can_take_from_an_infinite_stream() {
        final Stream<Integer> stream = Streams.generate(addOne(), 1);
        assertThat(stream.takeWhile(lessThan(10)), contains(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    @Test public void
    is_repeatable() {
        final Stream<Integer> takenStream = Streams.of(1, 2, 4, 3, 5).takeWhile(mutable());
        assertThat(takenStream, contains(1, 2, 4));
        assertThat(takenStream, contains(1, 2, 4));
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

    private static Function<Integer, Integer> addOne() {
        return input -> input + 1;
    }
}
