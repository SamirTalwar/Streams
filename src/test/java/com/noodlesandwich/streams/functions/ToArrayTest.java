package com.noodlesandwich.streams.functions;

import java.util.Arrays;

import org.junit.Test;

import com.noodlesandwich.streams.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.is;

public class ToArrayTest {
    @Test public void
    converts_nil_to_an_empty_array() {
        assertThat(Stream.nil().toArray(Object.class), is(emptyArray()));
    }

    @Test public void
    converts_a_stream_to_an_equivalent_list() {
        Stream<Integer> stream = Stream.wrap(Arrays.asList(1, 2, 3));
        assertThat(stream.toArray(Integer.class), is(arrayContaining(1, 2, 3)));
    }
}