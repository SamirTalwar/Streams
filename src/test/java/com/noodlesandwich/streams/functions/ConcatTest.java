package com.noodlesandwich.streams.functions;

import org.junit.Test;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.testutils.ThrowingIterator;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public final class ConcatTest {
    @Test public void
    is_lazy() {
        Stream<Object> stream = Stream.wrap(new ThrowingIterator());
        stream.concat(null);
    }

    @Test public void
    concatenates_two_nil_streams_into_one() {
        Stream<Object> streamOne = Stream.nil();
        Stream<Object> streamTwo = Stream.nil();
        assertThat(streamOne.concat(streamTwo), is(nil()));
    }

    @Test public void
    concatenates_a_populated_stream_with_a_nil_stream() {
        Stream<Integer> streamOne = Stream.of(1, 2, 3, 4, 5);
        Stream<Integer> streamTwo = Stream.nil();
        assertThat(streamOne.concat(streamTwo), contains(1, 2, 3, 4, 5));
    }

    @Test public void
    concatenates_a_nil_stream_with_a_populated_stream() {
        Stream<Integer> streamOne = Stream.nil();
        Stream<Integer> streamTwo = Stream.of(7, 6, 5, 4, 3);
        assertThat(streamOne.concat(streamTwo), contains(7, 6, 5, 4, 3));
    }

    @Test public void
    concatenates_two_populated_streams() {
        Stream<Integer> streamOne = Stream.of(1, 2, 3, 4, 5);
        Stream<Integer> streamTwo = Stream.of(7, 6, 5, 4, 3);
        assertThat(streamOne.concat(streamTwo), contains(1, 2, 3, 4, 5, 7, 6, 5, 4, 3));
    }

    @Test public void
    is_repeatable() {
        Stream<Integer> concatenatedStream = Stream.of(1, 2, 3, 4, 5).concat(Stream.of(7, 6, 5, 4, 3));
        assertThat(concatenatedStream, contains(1, 2, 3, 4, 5, 7, 6, 5, 4, 3));
        assertThat(concatenatedStream, contains(1, 2, 3, 4, 5, 7, 6, 5, 4, 3));
    }
}
