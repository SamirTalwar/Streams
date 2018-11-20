package com.noodlesandwich.streams.functions;

import org.junit.jupiter.api.Test;

import com.noodlesandwich.streams.FoldLeftFunction;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class FoldLeftTest {
    @Test public void
    works_with_a_single_type() {
        final Stream<Integer> stream = Streams.of(6, 3, 2, 7);
        assertThat(stream.foldLeft(summation(), 0), is(18));
    }

    @Test public void
    works_with_multiple_types() {
        final Stream<Integer> stream = Streams.of(6, 3, 2, 7);
        assertThat(stream.foldLeft(joinAsString(), ""), is(", 6, 3, 2, 7"));
    }

    private static FoldLeftFunction<Integer, Integer> summation() {
        return (accumulator, input) -> accumulator + input;
    }

    private static FoldLeftFunction<String, Object> joinAsString() {
        return (accumulator, input) -> accumulator + ", " + input;
    }
}
