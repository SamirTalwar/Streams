package com.noodlesandwich.streams.functions;

import org.junit.jupiter.api.Test;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;
import com.noodlesandwich.streams.testutils.ThrowingIterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;

public final class ConcatTest {
    @Test public void
    is_lazy() {
        final Stream<Object> stream = Streams.wrap(new ThrowingIterator());
        stream.concat(null);
    }

    @Test public void
    concatenates_two_nil_streams_into_one() {
        final Stream<Object> streamOne = Streams.nil();
        final Stream<Object> streamTwo = Streams.nil();
        assertThat(streamOne.concat(streamTwo), is(nil()));
    }

    @Test public void
    concatenates_a_populated_stream_with_a_nil_stream() {
        final Stream<Integer> streamOne = Streams.of(1, 2, 3, 4, 5);
        final Stream<Integer> streamTwo = Streams.nil();
        assertThat(streamOne.concat(streamTwo), contains(1, 2, 3, 4, 5));
    }

    @Test public void
    concatenates_a_nil_stream_with_a_populated_stream() {
        final Stream<Integer> streamOne = Streams.nil();
        final Stream<Integer> streamTwo = Streams.of(7, 6, 5, 4, 3);
        assertThat(streamOne.concat(streamTwo), contains(7, 6, 5, 4, 3));
    }

    @Test public void
    concatenates_two_populated_streams() {
        final Stream<Integer> streamOne = Streams.of(1, 2, 3, 4, 5);
        final Stream<Integer> streamTwo = Streams.of(7, 6, 5, 4, 3);
        assertThat(streamOne.concat(streamTwo), contains(1, 2, 3, 4, 5, 7, 6, 5, 4, 3));
    }

    @Test public void
    is_repeatable() {
        final Stream<Integer> concatenatedStream = Streams.of(1, 2, 3, 4, 5).concat(Streams.of(7, 6, 5, 4, 3));
        assertThat(concatenatedStream, contains(1, 2, 3, 4, 5, 7, 6, 5, 4, 3));
        assertThat(concatenatedStream, contains(1, 2, 3, 4, 5, 7, 6, 5, 4, 3));
    }
}
