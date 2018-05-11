package com.noodlesandwich.streams.functions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;
import com.noodlesandwich.streams.testutils.ThrowingIterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;

public final class DropTest {
    @Test public void
    is_lazy() {
        final Stream<Object> stream = Streams.wrap(new ThrowingIterator());
        stream.drop(1);
    }

    @Test public void
    dropping_zero_elements_does_nothing() {
        final Stream<Integer> stream = Streams.of(1, 2, 3, 4, 5);
        assertThat(stream.drop(0), contains(1, 2, 3, 4, 5));
    }

    @Test public void
    drops_the_first_N_elements_of_a_stream() {
        final Stream<Integer> stream = Streams.of(1, 2, 3, 4, 5);
        assertThat(stream.drop(3), contains(4, 5));
    }

    @Test public void
    dropping_more_than_the_size_of_the_stream_returns_nil() {
        final Stream<Object> stream = Streams.of(new Object(), new Object(), new Object());
        assertThat(stream.drop(7), is(nil()));
    }

    @Test public void
    cannot_drop_a_negative_number_of_elements() {
        final Stream<Integer> stream = Streams.of(1, 2, 3, 4, 5);
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() {
                stream.drop(-1);
            }
        });
    }

    @Test public void
    is_repeatable() {
        final Stream<Integer> droppedStream = Streams.of(1, 2, 3, 4, 5).drop(3);
        assertThat(droppedStream, contains(4, 5));
        assertThat(droppedStream, contains(4, 5));
    }
}
