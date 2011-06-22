package com.noodlesandwich.streams.functions;

import java.util.Map;

import org.junit.Test;

import com.google.common.base.Function;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public final class GroupByTest {
    @Test public void
    converts_nil_to_an_empty_map() {
        assertThat(Streams.nil().groupBy(null).isEmpty(), is(true));
    }

    @Test public void
    groups_a_stream_using_the_function_provided_to_generate_keys() {
        Stream<Integer> stream = Streams.of(1, 2, 3, 4, 5);

        Map<Integer, Stream<Integer>> groupedStream = stream.groupBy(mod(2));
        assertThat(groupedStream.get(0), contains(2, 4));
        assertThat(groupedStream.get(1), contains(1, 3, 5));
    }

    @Test public void
    works_with_infinite_streams() {
        Stream<Integer> stream = Streams.generate(add(1), 1);

        Map<Integer, Stream<Integer>> groupedStream = stream.groupBy(mod(2));
        assertThat(groupedStream.get(0).take(10), contains(2, 4, 6, 8, 10, 12, 14, 16, 18, 20));
    }

    private static Function<Integer, Integer> add(final int n) {
        return new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer input) {
                return input + n;
            }
        };
    }

    private static Function<Integer, Integer> mod(final int n) {
        return new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer input) {
                return input % n;
            }
        };
    }
}
