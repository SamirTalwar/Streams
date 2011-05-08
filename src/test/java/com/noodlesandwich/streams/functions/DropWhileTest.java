package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.Streams;
import org.junit.Test;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.testutils.ThrowingIterator;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public final class DropWhileTest {
    @Test public void
    is_lazy() {
        Stream<Object> stream = Streams.wrap(new ThrowingIterator());
        stream.dropWhile(null);
    }

    @Test public void
    dropping_while_true_returns_nil() {
        Stream<Object> stream = Streams.of(new Object(), new Object(), new Object());
        assertThat(stream.dropWhile(Predicates.alwaysTrue()), is(nil()));
    }

    @Test public void
    drops_elements_of_a_stream_while_the_predicate_evaluates_to_true() {
        Stream<Integer> stream = Streams.of(1, 2, 4, 3, 5);
        assertThat(stream.dropWhile(lessThan(4)), contains(4, 3, 5));
    }

    @Test public void
    dropping_while_false_does_nothing() {
        Stream<Integer> stream = Streams.of(1, 2, 3, 4, 5);
        assertThat(stream.dropWhile(Predicates.<Integer>alwaysFalse()), contains(1, 2, 3, 4, 5));
    }

    @Test public void
    is_repeatable() {
        Stream<Integer> droppedStream = Streams.of(1, 2, 4, 3, 5).dropWhile(mutable());
        assertThat(droppedStream, contains(3, 5));
        assertThat(droppedStream, contains(3, 5));
    }

    private static Predicate<Integer> lessThan(final int n) {
        return new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input < n;
            }
        };
    }

    private static Predicate<Object> mutable() {
        return new Predicate<Object>() {
            private int i = 3;
            @Override
            public boolean apply(Object input) {
                return i-- > 0;
            }
        };
    }
}
