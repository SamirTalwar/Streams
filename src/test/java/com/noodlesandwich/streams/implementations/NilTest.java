package com.noodlesandwich.streams.implementations;

import org.junit.jupiter.api.Test;

import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Streams;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;

public final class NilTest {
    @Test public void
    nil_is_nil() {
        assertThat(Streams.nil(), is(nil()));
    }

    @Test public void
    throws_an_exception_if_head_is_called() {
        assertThrows(EndOfStreamException.class, () -> Streams.nil().head());
    }

    @Test public void
    throws_an_exception_if_tail_is_called() {
        assertThrows(EndOfStreamException.class, () -> Streams.nil().tail());
    }

    @Test public void
    iterator_stops_immediately() {
        assertThat(Streams.nil(), is(emptyIterable()));
    }
}
