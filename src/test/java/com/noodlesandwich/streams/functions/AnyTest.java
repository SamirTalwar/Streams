package com.noodlesandwich.streams.functions;

import org.junit.Test;

import com.google.common.base.Predicates;
import com.noodlesandwich.streams.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class AnyTest {
    @Test public void
    returns_false_when_there_are_no_elements() {
        assertThat(Stream.nil().any(null), is(false));
    }

    @Test public void
    returns_true_when_a_single_element_matches_the_predicate() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        assertThat(stream.any(Predicates.equalTo(3)), is(true));
    }

    @Test public void
    returns_false_when_no_elements_match_the_predicate() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        assertThat(stream.any(Predicates.equalTo(6)), is(false));
    }
}
