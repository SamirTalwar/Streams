package com.noodlesandwich.streams.functions;

import java.util.Iterator;

import org.junit.Test;

import com.noodlesandwich.streams.Stream;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public final class TakeTest {
    @Test public void
    taking_zero_elements_returns_nil() {
        Stream<Object> stream = Stream.of(new Object(), new Object(), new Object());
        assertThat(stream.take(0), is(nil()));
    }

    @Test public void
    takes_the_first_N_elements_of_a_stream() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        assertThat(stream.take(3), contains(1, 2, 3));
    }

    @Test public void
    taking_more_than_the_size_of_the_stream_does_nothing() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        assertThat(stream.take(7), contains(1, 2, 3, 4, 5));
    }

    @Test public void
    can_take_from_an_infinite_stream() {
        Stream<Integer> stream = Stream.wrap(new Repeater(5));
        assertThat(stream.take(7), contains(5, 5, 5, 5, 5, 5, 5));
    }

    @Test(expected=IllegalArgumentException.class) public void
    cannot_take_a_negative_number_of_elements() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        stream.take(-1);
    }

    private static final class Repeater implements Iterator<Integer> {
        private final int n;

        public Repeater(final int n) {
            this.n = n;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Integer next() {
            return n;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
