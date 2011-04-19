package com.noodlesandwich.streams.functions;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.testutils.ThrowingIterator;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public final class MapTest {
    @Test public void
    is_lazy() {
        Stream<Object> stream = Stream.wrap(new ThrowingIterator());
        stream.map(null);
    }

    @Test public void
    a_mapping_of_nil_to_anything_is_nil() {
        assertThat(Stream.nil().map(Functions.constant(new Object())), is(nil()));
    }

    @Test public void
    a_mapping_of_a_stream_via_a_function_returns_a_new_stream() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        assertThat(stream.map(toStringFunc()), contains("1", "2", "3"));
    }

    private static Function<Integer, String> toStringFunc() {
        return new Function<Integer, String>() {
            @Override
            public String apply(Integer input) {
                return input.toString();
            }
        };
    }
}
