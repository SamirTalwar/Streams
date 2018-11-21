package com.noodlesandwich.streams.functions;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

public final class ToMapTest {
    @Test
    public void
    converts_nil_to_an_empty_map() {
        assertThat(Streams.nil().toMap(null).isEmpty(), is(true));
    }

    @Test
    public void
    converts_a_stream_to_a_map_using_the_function_provided_to_generate_values() {
        final Stream<Integer> stream = Streams.of(1, 2, 3);
        assertThat(stream.toMap(add(7)), allOf(
                hasEntry(1, 8),
                hasEntry(2, 9),
                hasEntry(3, 10)));
    }

    @Test
    public void
    keys_and_values_can_be_different_types() {
        final Stream<Integer> stream = Streams.of(1, 2, 3);
        assertThat(stream.toMap(Object::toString), allOf(
                hasEntry(1, "1"),
                hasEntry(2, "2"),
                hasEntry(3, "3")));
    }

    @Test
    public void
    removes_duplicates() {
        final Stream<Integer> stream = Streams.of(2, 3, 2, 1);
        assertThat(stream.toMap(add(7)), allOf(
                hasEntry(1, 8),
                hasEntry(2, 9),
                hasEntry(3, 10)));
    }

    private static Function<Integer, Integer> add(final int n) {
        return input -> input + n;
    }
}
