package com.noodlesandwich.streams.implementations;

import org.junit.Test;

import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Stream;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;

public class NilTest {
    @Test public void
    nil_is_nil() {
        assertThat(Stream.nil(), is(nil()));
    }

    @Test(expected=EndOfStreamException.class) public void
    throws_an_exception_if_head_is_called() {
        Stream.nil().head();
    }

    @Test(expected=EndOfStreamException.class) public void
    throws_an_exception_if_tail_is_called() {
        Stream.nil().tail();
    }

    @Test public void
    iterator_stops_immediately() {
        assertThat(Stream.nil(), is(emptyIterable()));
    }
}
