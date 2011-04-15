package com.noodlesandwich.streams.functions;

import java.util.Arrays;

import org.junit.Test;

import com.noodlesandwich.streams.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class ContainsTest {
    @Test public void
    nil_does_not_contain_anything() {
        assertThat(Stream.nil().contains(new Object()), is(false));
    }

    @Test public void
    a_stream_contains_its_elements() {
        Stream<Integer> stream = Stream.wrap(Arrays.asList(1, 2, 3));
        assertThat(stream.contains(2), is(true));
    }

    @Test public void
    a_stream_does_not_contain_anything_else() {
        Stream<Integer> stream = Stream.wrap(Arrays.asList(1, 2, 3));
        assertThat(stream.contains(4), is(false));
    }
}
