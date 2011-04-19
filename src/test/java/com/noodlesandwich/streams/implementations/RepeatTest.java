package com.noodlesandwich.streams.implementations;

import org.junit.Test;

import com.noodlesandwich.streams.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public final class RepeatTest {
    @Test public void
    repeats_a_value() {
        Stream<Integer> stream = Stream.repeat(7);
        assertThat(stream.take(10), contains(7, 7, 7, 7, 7, 7, 7, 7, 7, 7));
    }
}
