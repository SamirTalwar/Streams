package com.noodlesandwich.streams.functions;

import java.util.Arrays;

import org.junit.Test;

import com.noodlesandwich.streams.Stream;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class ConcatTest {
    @Test public void
    concatenates_two_nil_streams_into_one() {
        Stream<Object> streamOne = Stream.nil();
        Stream<Object> streamTwo = Stream.nil();
        assertThat(streamOne.concat(streamTwo), is(nil()));
    }

    @Test public void
    concatenates_a_populated_stream_with_a_nil_stream() {
        Stream<Integer> streamOne = Stream.wrap(Arrays.asList(1, 2, 3, 4, 5));
        Stream<Integer> streamTwo = Stream.nil();
        assertThat(streamOne.concat(streamTwo), contains(1, 2, 3, 4, 5));
    }

    @Test public void
    concatenates_a_nil_stream_with_a_populated_stream() {
        Stream<Integer> streamOne = Stream.nil();
        Stream<Integer> streamTwo = Stream.wrap(Arrays.asList(7, 6, 5, 4, 3));
        assertThat(streamOne.concat(streamTwo), contains(7, 6, 5, 4, 3));
    }

    @Test public void
    concatenates_two_populated_streams() {
        Stream<Integer> streamOne = Stream.wrap(Arrays.asList(1, 2, 3, 4, 5));
        Stream<Integer> streamTwo = Stream.wrap(Arrays.asList(7, 6, 5, 4, 3));
        assertThat(streamOne.concat(streamTwo), contains(1, 2, 3, 4, 5, 7, 6, 5, 4, 3));
    }
}
