package com.noodlesandwich.streams.functions;

import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.noodlesandwich.streams.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class TakeTest {
    @Test public void
    taking_zero_elements_returns_nil() {
        Stream<Integer> stream = Stream.wrap(Arrays.asList(1, 2, 3, 4, 5));
        assertThat(stream.take(0), is(Matchers.<Integer>emptyIterable()));
    }

    @Test public void
    takes_the_first_N_elements_of_a_stream() {
        Stream<Integer> stream = Stream.wrap(Arrays.asList(1, 2, 3, 4, 5));
        assertThat(stream.take(3), contains(1, 2, 3));
    }

    @Test public void
    taking_more_than_the_size_of_the_stream_does_nothing() {
        Stream<Integer> stream = Stream.wrap(Arrays.asList(1, 2, 3, 4, 5));
        assertThat(stream.take(7), contains(1, 2, 3, 4, 5));
    }

    @Test(expected=IllegalArgumentException.class) public void
    cannot_take_a_negative_number_of_elements() {
        Stream<Integer> stream = Stream.wrap(Arrays.asList(1, 2, 3, 4, 5));
        stream.take(-1);
    }
}
