package com.noodlesandwich.streams.functions;

import org.junit.jupiter.api.Test;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;
import com.noodlesandwich.streams.testutils.ThrowingIterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;

public final class MapTest {
    @Test public void
    is_lazy() {
        final Stream<Object> stream = Streams.wrap(new ThrowingIterator());
        stream.map(null);
    }

    @Test public void
    a_mapping_of_nil_to_anything_is_nil() {
        assertThat(Streams.nil().map(Functions.constant(new Object())), is(nil()));
    }

    @Test public void
    a_mapping_of_a_stream_via_a_function_returns_a_new_stream() {
        final Stream<Integer> stream = Streams.of(1, 2, 3);
        assertThat(stream.map(toStringFunc()), contains("1", "2", "3"));
    }

    @Test public void
    is_repeatable() {
        final Stream<Integer> mappedStream = Streams.of(9, 7, 2).map(addIncrementingNumbers());
        assertThat(mappedStream, contains(10, 9, 5));
        assertThat(mappedStream, contains(10, 9, 5));
    }

    private static Function<Object, String> toStringFunc() {
        return new Function<Object, String>() {
            @Override
            public String apply(final Object input) {
                return input.toString();
            }
        };
    }

    private static Function<Integer, Integer> addIncrementingNumbers() {
        return new Function<Integer, Integer>() {
            private int i = 0;
            @Override
            public Integer apply(final Integer input) {
                return input + (++i);
            }
        };
    }
}
