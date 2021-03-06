package com.noodlesandwich.streams.implementations;

import org.junit.jupiter.api.Test;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public final class RepeatTest {
    @Test public void
    repeats_a_value() {
        final Stream<Integer> stream = Streams.repeat(7);
        assertThat(stream.take(10), contains(7, 7, 7, 7, 7, 7, 7, 7, 7, 7));
    }
}
