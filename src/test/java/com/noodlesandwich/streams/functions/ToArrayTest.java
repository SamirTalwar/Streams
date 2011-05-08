package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.Streams;
import org.junit.Test;

import com.noodlesandwich.streams.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.is;

public final class ToArrayTest {
    @Test public void
    converts_nil_to_an_empty_array() {
        assertThat(Streams.nil().toArray(Object.class), is(emptyArray()));
    }

    @Test public void
    converts_a_stream_to_an_equivalent_list() {
        Stream<Integer> stream = Streams.of(1, 2, 3);
        assertThat(stream.toArray(Integer.class), is(arrayContaining(1, 2, 3)));
    }

    @Test public void
    retains_order() {
        Stream<Integer> stream = Streams.of(3, 2, 1);
        assertThat(stream.toArray(Integer.class), is(arrayContaining(3, 2, 1)));
    }

    @Test public void
    retains_duplicates() {
        Stream<Integer> stream = Streams.of(2, 3, 2, 1);
        assertThat(stream.toArray(Integer.class), is(arrayContaining(2, 3, 2, 1)));
    }
}
