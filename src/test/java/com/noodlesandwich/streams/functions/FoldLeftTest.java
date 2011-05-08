package com.noodlesandwich.streams.functions;

import com.noodlesandwich.streams.Streams;
import org.junit.Test;

import com.noodlesandwich.streams.FoldLeftFunction;
import com.noodlesandwich.streams.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class FoldLeftTest {
    @Test public void
    works_with_a_single_type() {
        Stream<Integer> stream = Streams.of(6, 3, 2, 7);
        assertThat(stream.foldLeft(summation(), 0), is(18));
    }

    @Test public void
    works_with_multiple_types() {
        Stream<Integer> stream = Streams.of(6, 3, 2, 7);
        assertThat(stream.foldLeft(joinAsString(), ""), is(", 6, 3, 2, 7"));
    }

    private static FoldLeftFunction<Integer, Integer> summation() {
        return new FoldLeftFunction<Integer, Integer>() {
            @Override
            public Integer apply(Integer accumulator, Integer input) {
                return accumulator + input;
            }
        };
    }

    private static FoldLeftFunction<String, Object> joinAsString() {
        return new FoldLeftFunction<String, Object>() {
            @Override
            public String apply(String accumulator, Object input) {
                return accumulator + ", " + input;
            }
        };
    }
}
