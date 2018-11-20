package com.noodlesandwich.streams.functions;

import org.junit.jupiter.api.Test;

import com.noodlesandwich.streams.FoldRightFunction;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class FoldRightTest {
    @Test public void
    works_with_a_single_type() {
        final Stream<Integer> stream = Streams.of(6, 3, 2, 7);
        assertThat(stream.foldRight(summation(), 0), is(18));
    }

    @Test public void
    works_with_multiple_types() {
        final Stream<Integer> stream = Streams.of(6, 3, 2, 7);
        assertThat(stream.foldRight(joinAsString(), ""), is("6, 3, 2, 7, "));
    }

    private static FoldRightFunction<Integer, Integer> summation() {
        return (accumulator, input) -> accumulator + input;
    }

    private static FoldRightFunction<Object, String> joinAsString() {
        return (input, accumulator) -> input + ", " + accumulator;
    }
}
