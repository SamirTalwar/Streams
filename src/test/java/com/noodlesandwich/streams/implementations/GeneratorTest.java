package com.noodlesandwich.streams.implementations;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public final class GeneratorTest {
    @Test public void
    generates_a_stream_using_the_function() {
        final Stream<Integer> stream = Streams.generate(multiplyBy(2), 1);
        assertThat(stream.take(10), contains(1, 2, 4, 8, 16, 32, 64, 128, 256, 512));
    }

    @Test public void
    stops_when_an_EndOfStreamException_is_thrown() {
        final Stream<Integer> stream = Streams.generate(addOneAndThrowAfterTen(), 1);
        assertThat(stream, contains(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    @Test public void
    can_act_twice() {
        final Stream<Integer> stream = Streams.generate(mutableFibonacci(), 1);
        assertThat(stream.take(10), contains(1, 1, 2, 3, 5, 8, 13, 21, 34, 55));
        assertThat(stream.take(10), contains(1, 1, 2, 3, 5, 8, 13, 21, 34, 55));
    }

    private static Function<Integer, Integer> multiplyBy(final int n) {
        return input -> input * n;
    }

    private static Function<Integer, Integer> addOneAndThrowAfterTen() {
        return input -> {
            if (input >= 10) {
                throw new EndOfStreamException();
            }

            return input + 1;
        };
    }

    private static Function<Integer, Integer> mutableFibonacci() {
        return new Function<>() {
            private int first = 0;
            private int second = 1;

            @Override
            public Integer apply(final Integer input) {
                final int value = first == 0 ? 1 : first + second;
                first = second;
                second = value;
                return value;
            }
        };
    }
}
