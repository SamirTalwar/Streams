package com.noodlesandwich.streams.functions;

import org.junit.Test;

import com.noodlesandwich.streams.Stream;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public final class UnionTest {
    @Test public void
    unioning_nil_with_nil_returns_nil() {
        assertThat(Stream.nil().union(Stream.nil()), is(nil()));
    }

    @Test public void
    unions_a_stream_with_nil() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        assertThat(stream.union(Stream.<Integer>nil()), contains(1, 2, 3));
    }

    @Test public void
    unions_nil_with_a_stream() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        assertThat(Stream.<Integer>nil().union(stream), contains(1, 2, 3));
    }

    @Test public void
    concatenates_two_streams() {
        Stream<Integer> streamOne = Stream.of(1, 2, 3);
        Stream<Integer> streamTwo = Stream.of(6, 5, 4);
        assertThat(streamOne.union(streamTwo), contains(1, 2, 3, 6, 5, 4));
    }

    @Test public void
    removes_duplicates_from_each_stream() {
        Stream<Integer> streamOne = Stream.of(1, 2, 3, 2);
        Stream<Integer> streamTwo = Stream.of(6, 5, 5, 4);
        assertThat(streamOne.union(streamTwo), contains(1, 2, 3, 6, 5, 4));
    }

    @Test public void
    removes_duplicates_across_the_streams() {
        Stream<Integer> streamOne = Stream.of(1, 2, 3);
        Stream<Integer> streamTwo = Stream.of(2, 3, 4);
        assertThat(streamOne.union(streamTwo), contains(1, 2, 3, 4));
    }
}
