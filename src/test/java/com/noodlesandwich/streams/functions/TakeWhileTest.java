package com.noodlesandwich.streams.functions;

import java.util.Arrays;

import org.junit.Test;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.noodlesandwich.streams.Stream;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public final class TakeWhileTest {
    @Test public void
    taking_while_false_returns_nil() {
        Stream<Object> stream = Stream.wrap(Arrays.asList(new Object(), new Object(), new Object()));
        assertThat(stream.takeWhile(Predicates.alwaysFalse()), is(nil()));
    }

    @Test public void
    takes_elements_of_a_stream_while_the_predicate_evaluates_to_true() {
        Stream<Integer> stream = Stream.wrap(Arrays.asList(1, 2, 4, 3, 5));
        assertThat(stream.takeWhile(lessThan(4)), contains(1, 2));
    }

    @Test public void
    taking_while_true_does_nothing() {
        Stream<Integer> stream = Stream.wrap(Arrays.asList(1, 2, 3, 4, 5));
        assertThat(stream.takeWhile(Predicates.<Integer>alwaysTrue()), contains(1, 2, 3, 4, 5));
    }

    private static Predicate<Integer> lessThan(final int n) {
        return new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input < n;
            }
        };
    }
}
