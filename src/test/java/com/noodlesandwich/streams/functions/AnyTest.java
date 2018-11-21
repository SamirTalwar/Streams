package com.noodlesandwich.streams.functions;

import org.junit.jupiter.api.Test;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;

import static java.util.function.Predicate.isEqual;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class AnyTest {
    @Test public void
    returns_false_when_there_are_no_elements() {
        assertThat(Streams.nil().any(null), is(false));
    }

    @Test public void
    returns_true_when_a_single_element_matches_the_predicate() {
        final Stream<Integer> stream = Streams.of(1, 2, 3, 4, 5);
        assertThat(stream.any(isEqual(3)), is(true));
    }

    @Test public void
    returns_false_when_no_elements_match_the_predicate() {
        final Stream<Integer> stream = Streams.of(1, 2, 3, 4, 5);
        assertThat(stream.any(isEqual(6)), is(false));
    }
}
