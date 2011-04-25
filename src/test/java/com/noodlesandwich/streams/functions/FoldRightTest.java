package com.noodlesandwich.streams.functions;

import org.junit.Test;

import com.noodlesandwich.streams.FoldFunction;
import com.noodlesandwich.streams.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class FoldRightTest {
    @Test public void
    works_with_a_single_type() {
        Stream<Integer> stream = Stream.of(6, 3, 2, 7);
        assertThat(stream.foldRight(summation(), 0), is(18));
    }

    @Test public void
    works_with_multiple_types() {
        Stream<Integer> stream = Stream.of(6, 3, 2, 7);
        assertThat(stream.foldRight(joinAsString(), ""), is("6, 3, 2, 7, "));
    }

    private static FoldFunction<Integer, Integer> summation() {
        return new FoldFunction<Integer, Integer>() {
            @Override
            public Integer apply(Integer accumulator, Integer input) {
                return accumulator + input;
            }
        };
    }

    private static FoldFunction<Object, String> joinAsString() {
        return new FoldFunction<Object, String>() {
            @Override
            public String apply(String accumulator, Object input) {
                return input + ", " + accumulator;
            }
        };
    }
}
