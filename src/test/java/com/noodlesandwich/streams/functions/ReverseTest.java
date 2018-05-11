package com.noodlesandwich.streams.functions;

import org.junit.jupiter.api.Test;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;
import com.noodlesandwich.streams.testutils.ThrowingIterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;

public final class ReverseTest {
    @Test public void
    is_lazy() {
        final Stream<Object> stream = Streams.wrap(new ThrowingIterator());
        stream.reverse();
    }

    @Test public void
    the_reverse_of_nil_is_nil() {
        assertThat(Streams.nil().reverse(), is(nil()));
    }

    @Test public void
    reverses_a_stream() {
        final Stream<Integer> stream = Streams.of(1, 2, 3);
        assertThat(stream.reverse(), contains(3, 2, 1));
    }

    @Test public void
    is_repeatable() {
        final Stream<Integer> reversedStream = Streams.of(1, 2, 3).reverse();
        assertThat(reversedStream, contains(3, 2, 1));
        assertThat(reversedStream, contains(3, 2, 1));
    }
}
