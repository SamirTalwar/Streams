package com.noodlesandwich.streams.functions;

import org.junit.jupiter.api.Test;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;
import com.noodlesandwich.streams.ZipWithFunction;
import com.noodlesandwich.streams.testutils.ThrowingIterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;

public final class ZipWithTest {
    @Test public void
    is_lazy() {
        final Stream<Object> stream = Streams.wrap(new ThrowingIterator());
        stream.zipWith(null, null);
    }

    @Test public void
    zipping_anything_with_nil_returns_nil() {
        final Stream<Integer> streamOne = Streams.of(1, 2, 3, 4, 5);
        final Stream<Integer> streamTwo = Streams.nil();
        assertThat(streamOne.zipWith(streamTwo, add()), is(nil()));
    }

    @Test public void
    zipping_nil_with_anything_returns_nil() {
        final Stream<Integer> streamOne = Streams.nil();
        final Stream<Integer> streamTwo = Streams.of(7, 6, 5, 4, 3);
        assertThat(streamOne.zipWith(streamTwo, add()), is(nil()));
    }

    @Test public void
    zips_two_populated_streams_of_the_same_length_and_type() {
        final Stream<Integer> streamOne = Streams.of(1, 2, 3, 4, 5);
        final Stream<Integer> streamTwo = Streams.of(7, 4, 9, 1, 10);
        assertThat(streamOne.zipWith(streamTwo, add()), contains(8, 6, 12, 5, 15));
    }

    @Test public void
    truncates_a_pairing_of_a_long_stream_with_a_short_stream_using_the_short() {
        final Stream<Integer> streamOne = Streams.of(1, 2, 3, 4, 5);
        final Stream<Integer> streamTwo = Streams.of(7, 4, 9);
        assertThat(streamOne.zipWith(streamTwo, add()), contains(8, 6, 12));
    }

    @Test public void
    truncates_a_pairing_of_a_short_stream_with_a_long_stream_using_the_short() {
        final Stream<Integer> streamOne = Streams.of(1, 2, 3);
        final Stream<Integer> streamTwo = Streams.of(7, 4, 9, 1, 10);
        assertThat(streamOne.zipWith(streamTwo, add()), contains(8, 6, 12));
    }

    @Test public void
    zips_streams_of_different_types() {
        final Stream<Integer> streamOne = Streams.of(1, 2, 3, 4, 5);
        final Stream<String> streamTwo = Streams.of("a", "b", "c", "d", "e");
        assertThat(streamOne.zipWith(streamTwo, concat()), contains("1a", "2b", "3c", "4d", "5e"));
    }

    @Test public void
    is_repeatable() {
        final Stream<Integer> zippedStream = Streams.of(1, 2, 3, 4, 5).zipWith(Streams.of(6, 7, 8, 9, 10), mutable());
        assertThat(zippedStream, contains(8, 11, 14, 17, 20));
        assertThat(zippedStream, contains(8, 11, 14, 17, 20));
    }

    private static ZipWithFunction<Integer, Integer, Integer> add() {
        return (a, b) -> a + b;
    }

    private static ZipWithFunction<Object, Object, String> concat() {
        return (a, b) -> a.toString() + b.toString();
    }

    private static ZipWithFunction<Integer, Integer, Integer> mutable() {
        return new ZipWithFunction<>() {
            private int i = 0;

            @Override
            public Integer apply(final Integer a, final Integer b) {
                return a + b + (++i);
            }
        };
    }
}
