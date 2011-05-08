package com.noodlesandwich.streams.implementations;

import com.noodlesandwich.streams.Streams;
import org.junit.Test;

import com.noodlesandwich.streams.EndOfStreamException;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;

public final class NilTest {
    @Test public void
    nil_is_nil() {
        assertThat(Streams.nil(), is(nil()));
    }

    @Test(expected=EndOfStreamException.class) public void
    throws_an_exception_if_head_is_called() {
        Streams.nil().head();
    }

    @Test(expected=EndOfStreamException.class) public void
    throws_an_exception_if_tail_is_called() {
        Streams.nil().tail();
    }

    @Test public void
    iterator_stops_immediately() {
        assertThat(Streams.nil(), is(emptyIterable()));
    }
}
