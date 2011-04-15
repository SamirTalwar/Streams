package com.noodlesandwich.streams.functions;

import java.util.Arrays;

import org.junit.Test;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.matchers.NilMatcher;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public final class IntersectionTest {
    @Test public void
    intersecting_anything_with_nil_returns_nil() {
        Stream<Object> stream = Stream.wrap(Arrays.asList(new Object(), new Object(), new Object()));
        assertThat(stream.intersect(Stream.nil()), is(nil()));
    }

    @Test public void
    intersecting_nil_with_anything_returns_nil() {
        Stream<Object> stream = Stream.wrap(Arrays.asList(new Object(), new Object(), new Object()));
        assertThat(Stream.nil().intersect(stream), is(nil()));
    }

    @Test public void
    the_intersection_of_two_disjunct_streams_is_nil() {
        Stream<Integer> streamOne = Stream.wrap(Arrays.asList(1, 2, 3));
        Stream<Integer> streamTwo = Stream.wrap(Arrays.asList(6, 5, 4));
        assertThat(streamOne.intersect(streamTwo), is(NilMatcher.<Integer>nil()));
    }

    @Test public void
    intersects_two_streams() {
        Stream<Integer> streamOne = Stream.wrap(Arrays.asList(1, 2, 3));
        Stream<Integer> streamTwo = Stream.wrap(Arrays.asList(4, 3, 2));
        assertThat(streamOne.intersect(streamTwo), contains(2, 3));
    }

    @Test public void
    removes_duplicates() {
        Stream<Integer> streamOne = Stream.wrap(Arrays.asList(1, 2, 3, 2));
        Stream<Integer> streamTwo = Stream.wrap(Arrays.asList(2, 3, 4, 4));
        assertThat(streamOne.intersect(streamTwo), contains(2, 3));
    }
}
