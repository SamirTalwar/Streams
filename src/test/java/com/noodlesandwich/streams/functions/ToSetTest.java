package com.noodlesandwich.streams.functions;

import org.junit.jupiter.api.Test;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

public final class ToSetTest {
    @Test public void
    converts_nil_to_an_empty_set() {
        assertThat(Streams.nil().toSet(), is(empty()));
    }

    @Test public void
    converts_a_stream_to_an_equivalent_set() {
        final Stream<Integer> stream = Streams.of(1, 2, 3);
        assertThat(stream.toSet(), containsInAnyOrder(1, 2, 3));
    }

    @Test public void
    removes_duplicates() {
        final Stream<Integer> stream = Streams.of(2, 3, 2, 1);
        assertThat(stream.toSet(), containsInAnyOrder(1, 2, 3));
    }
}
