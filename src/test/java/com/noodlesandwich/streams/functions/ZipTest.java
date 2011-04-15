package com.noodlesandwich.streams.functions;

import org.junit.Test;

import com.noodlesandwich.streams.Pair;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.matchers.NilMatcher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public final class ZipTest {
    @Test public void
    zipping_anything_with_nil_returns_nil() {
        Stream<Integer> streamOne = Stream.of(1, 2, 3, 4, 5);
        Stream<Integer> streamTwo = Stream.nil();
        assertThat(streamOne.zip(streamTwo), is(NilMatcher.<Pair<Integer, Integer>>nil()));
    }

    @Test public void
    zipping_nil_with_anything_returns_nil() {
        Stream<Integer> streamOne = Stream.nil();
        Stream<Integer> streamTwo = Stream.of(7, 6, 5, 4, 3);
        assertThat(streamOne.zip(streamTwo), is(NilMatcher.<Pair<Integer, Integer>>nil()));
    }

    @SuppressWarnings("unchecked")
    @Test public void
    zips_two_populated_streams_of_the_same_length_and_type() {
        Stream<Integer> streamOne = Stream.of(1, 2, 3, 4, 5);
        Stream<Integer> streamTwo = Stream.of(7, 6, 5, 4, 3);
        assertThat(streamOne.zip(streamTwo), contains(
                pair(1, 7), pair(2, 6), pair(3, 5), pair(4, 4), pair(5, 3)
        ));
    }

    @SuppressWarnings("unchecked")
    @Test public void
    truncates_a_pairing_of_a_long_stream_with_a_short_stream_using_the_short() {
        Stream<Integer> streamOne = Stream.of(1, 2, 3, 4, 5);
        Stream<Integer> streamTwo = Stream.of(7, 6, 5);
        assertThat(streamOne.zip(streamTwo), contains(
                pair(1, 7), pair(2, 6), pair(3, 5)
        ));
    }

    @SuppressWarnings("unchecked")
    @Test public void
    truncates_a_pairing_of_a_short_stream_with_a_long_stream_using_the_short() {
        Stream<Integer> streamOne = Stream.of(1, 2, 3);
        Stream<Integer> streamTwo = Stream.of(7, 6, 5, 4, 3);
        assertThat(streamOne.zip(streamTwo), contains(
                pair(1, 7), pair(2, 6), pair(3, 5)
        ));
    }

    @SuppressWarnings("unchecked")
    @Test public void
    zips_streams_of_different_types() {
        Stream<Integer> streamOne = Stream.of(1, 2, 3, 4, 5);
        Stream<String> streamTwo = Stream.of("a", "b", "c", "d", "e");
        assertThat(streamOne.zip(streamTwo), contains(
                pair(1, "a"), pair(2, "b"), pair(3, "c"), pair(4, "d"), pair(5, "e")
        ));
    }

    private static <F, S> Pair<F, S> pair(F first, S second) {
        return new Pair<F, S>(first, second);
    }
}
