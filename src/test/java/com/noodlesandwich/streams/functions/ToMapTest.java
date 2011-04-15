package com.noodlesandwich.streams.functions;

import java.util.Arrays;

import org.junit.Test;

import com.google.common.base.Function;
import com.noodlesandwich.streams.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

public class ToMapTest {
    @Test public void
    converts_nil_to_an_empty_map() {
        assertThat(Stream.nil().toMap(null).isEmpty(), is(true));
    }

    @Test public void
    converts_a_stream_to_a_map_using_the_function_provided_to_generate_values() {
        Stream<Integer> stream = Stream.wrap(Arrays.asList(1, 2, 3));
        assertThat(stream.toMap(add(7)), allOf(hasEntry(1, 8),
                                               hasEntry(2, 9),
                                               hasEntry(3, 10)));
    }

    @Test public void
    removes_duplicates() {
        Stream<Integer> stream = Stream.wrap(Arrays.asList(2, 3, 2, 1));
        assertThat(stream.toMap(add(7)), allOf(hasEntry(1, 8),
                                               hasEntry(2, 9),
                                               hasEntry(3, 10)));
    }

    private Function<Integer, Integer> add(final int i) {
        return new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer input) {
                return input + i;
            }
        };
    }
}
