package com.noodlesandwich.streams.functions;

import org.junit.jupiter.api.Test;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class ContainsTest {
    @Test public void
    nil_does_not_contain_anything() {
        assertThat(Streams.nil().contains(new Object()), is(false));
    }

    @Test public void
    a_stream_contains_its_elements() {
        final Stream<Integer> stream = Streams.of(1, 2, 3);
        assertThat(stream.contains(2), is(true));
    }

    @Test public void
    a_stream_does_not_contain_anything_else() {
        final Stream<Integer> stream = Streams.of(1, 2, 3);
        assertThat(stream.contains(4), is(false));
    }
}
