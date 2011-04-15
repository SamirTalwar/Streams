package com.noodlesandwich.streams.functions;

import java.util.Arrays;

import org.junit.Test;

import com.noodlesandwich.streams.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

public class ToListTest {
    @Test public void
    converts_nil_to_an_empty_list() {
        assertThat(Stream.nil().toList(), is(empty()));
    }

    @Test public void
    converts_a_stream_to_an_equivalent_list() {
        Stream<Integer> stream = Stream.wrap(Arrays.asList(1, 2, 3));
        assertThat(stream.toList(), contains(1, 2, 3));
    }
}
