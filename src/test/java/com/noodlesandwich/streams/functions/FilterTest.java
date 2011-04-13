package com.noodlesandwich.streams.functions;

import java.util.Arrays;

import org.junit.Test;

import com.google.common.base.Predicate;
import com.noodlesandwich.streams.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class FilterTest {
    @Test public void
    filtering_a_stream_with_a_predicate_returns_a_new_stream() {
        Stream<Integer> stream = Stream.wrap(Arrays.asList(1, 3, 2, 4, 7, 8, 9, 6, 5));
        assertThat(stream.filter(isEven()), contains(2, 4, 8, 6));
    }

    private static Predicate<Integer> isEven() {
        return new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input % 2 == 0;
            }
        };
    }
}
