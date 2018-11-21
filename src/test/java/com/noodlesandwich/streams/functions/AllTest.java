package com.noodlesandwich.streams.functions;

import org.junit.jupiter.api.Test;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;

import static java.util.function.Predicate.isEqual;
import static java.util.function.Predicate.not;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class AllTest {
    @Test public void
    returns_true_when_there_are_no_elements() {
        assertThat(Streams.nil().all(null), is(true));
    }

    @Test public void
    returns_true_when_all_elements_match_the_predicate() {
        final Stream<Integer> stream = Streams.of(1, 2, 3, 4, 5);
        assertThat(stream.all(not(isEqual(6))), is(true));
    }

    @Test public void
    returns_false_when_any_element_does_not_match_the_predicate() {
        final Stream<Integer> stream = Streams.of(1, 2, 3, 4, 5);
        assertThat(stream.all(not(isEqual(3))), is(false));
    }
}
