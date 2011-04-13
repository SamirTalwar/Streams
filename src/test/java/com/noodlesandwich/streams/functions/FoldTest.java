package com.noodlesandwich.streams.functions;

import java.util.Arrays;

import com.noodlesandwich.streams.Stream;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FoldTest {
    @Test public void
    works_with_a_single_type() {
        Stream<Integer> stream = Stream.wrap(Arrays.asList(6, 3, 2, 7));
        assertThat(stream.fold(summation()).apply(0), is(18));
    }

    @Test public void
    works_with_a_multiple_types() {
        Stream<Integer> stream = Stream.wrap(Arrays.asList(6, 3, 2, 7));
        assertThat(stream.fold(joinAsString()).apply(""), is(", 6, 3, 2, 7"));
    }

    private static FoldFunction<Integer, Integer> summation() {
        return new FoldFunction<Integer, Integer>() {
            @Override
            public Integer apply(Integer accumulator, Integer input) {
                return accumulator + input;
            }
        };
    }

    private static FoldFunction<Integer, String> joinAsString() {
        return new FoldFunction<Integer, String>() {
            @Override
            public String apply(String accumulator, Integer input) {
                return accumulator + ", " + input;
            }
        };
    }
}
