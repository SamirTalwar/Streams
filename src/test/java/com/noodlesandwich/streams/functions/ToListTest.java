package com.noodlesandwich.streams.functions;

import org.junit.jupiter.api.Test;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

public final class ToListTest {
    @Test public void
    converts_nil_to_an_empty_list() {
        assertThat(Streams.nil().toList(), is(empty()));
    }

    @Test public void
    converts_a_stream_to_an_equivalent_list() {
        final Stream<Integer> stream = Streams.of(1, 2, 3);
        assertThat(stream.toList(), contains(1, 2, 3));
    }

    @Test public void
    retains_order() {
        final Stream<Integer> stream = Streams.of(3, 2, 1);
        assertThat(stream.toList(), contains(3, 2, 1));
    }

    @Test public void
    retains_duplicates() {
        final Stream<Integer> stream = Streams.of(2, 3, 2, 1);
        assertThat(stream.toList(), contains(2, 3, 2, 1));
    }
}
