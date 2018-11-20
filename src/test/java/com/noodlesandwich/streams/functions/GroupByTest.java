package com.noodlesandwich.streams.functions;

import org.junit.jupiter.api.Test;

import com.google.common.base.Function;
import com.noodlesandwich.streams.Lookup;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public final class GroupByTest {
    @Test public void
    groups_a_stream_using_the_function_provided_to_generate_keys() {
        final Stream<Integer> stream = Streams.of(1, 2, 3, 4, 5);

        final Lookup<Integer, Stream<Integer>> groupedStream = stream.groupBy(mod(2));
        assertThat(groupedStream.get(0), contains(2, 4));
        assertThat(groupedStream.get(1), contains(1, 3, 5));
    }

    @Test public void
    works_with_infinite_streams() {
        final Stream<Integer> stream = Streams.generate(add(1), 1);

        final Lookup<Integer, Stream<Integer>> groupedStream = stream.groupBy(mod(2));
        assertThat(groupedStream.get(0).take(10), contains(2, 4, 6, 8, 10, 12, 14, 16, 18, 20));
    }

    private static Function<Integer, Integer> add(final int n) {
        return input -> input + n;
    }

    private static Function<Integer, Integer> mod(final int n) {
        return input -> input % n;
    }
}
