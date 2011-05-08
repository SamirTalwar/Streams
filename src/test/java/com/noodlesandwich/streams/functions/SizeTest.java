package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.Streams;
import org.junit.Test;

import com.noodlesandwich.streams.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class SizeTest {
    @Test public void
    the_size_of_a_nil_stream_is_zero() {
        assertThat(Streams.nil().size(), is(0));
    }

    @Test public void
    the_size_of_a_stream_is_the_number_of_elements_in_the_stream() {
        Stream<Integer> stream = Streams.of(9, 2, 12, 3, 8);
        assertThat(stream.size(), is(5));
    }
}
