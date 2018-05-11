package com.noodlesandwich.streams.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;
import com.noodlesandwich.streams.testutils.ThrowingIterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static com.noodlesandwich.streams.matchers.HeadMatcher.has_a_head_of;
import static com.noodlesandwich.streams.matchers.NilMatcher.nil;
import static com.noodlesandwich.streams.matchers.TailMatcher.has_a_tail_of;

public final class IteratorWrapperTest {
    @Test public void
    wraps_an_iterable() {
        final Iterable<Integer> iterable = Arrays.asList(1, 2, 3);
        assertThat(Streams.wrap(iterable), contains(1, 2, 3));
    }

    @Test public void
    wraps_an_iterator() {
        final Iterator<Integer> iterator = Arrays.asList(1, 2, 3).iterator();
        assertThat(Streams.wrap(iterator), contains(1, 2, 3));
    }

    @Test public void
    is_lazy() {
        Streams.wrap(new ThrowingIterator());
    }

    @Test public void
    is_nil_when_the_iterable_is_empty() {
        final Iterable<Object> iterable = new ArrayList<Object>();
        assertThat(Streams.wrap(iterable), is(nil()));
    }

    @Test public void
    is_not_nil_when_the_iterable_has_items() {
        final Iterable<Object> iterable = Arrays.asList(new Object());
        assertThat(Streams.wrap(iterable), is(not(nil())));
    }

    @Test public void
    the_head_does_not_change() {
        final Stream<Integer> stream = Streams.of(1, 2, 3);
        final Integer head = stream.head();
        assertThat(stream, has_a_head_of(head));
    }

    @Test public void
    the_tail_does_not_change() {
        final Stream<Integer> stream = Streams.of(1, 2, 3);
        final Stream<Integer> tail = stream.tail();
        assertThat(stream, has_a_tail_of(tail));
    }

    @Test public void
    can_call_tail_before_head() {
        final Stream<Integer> stream = Streams.of(1, 2, 3);
        assertThat(stream.tail(), contains(2, 3));
        assertThat(stream.head(), is(1));
    }

    @Test public void
    throws_an_exception_when_head_is_called_on_an_empty_wrapper() {
        final Iterable<Object> iterable = new ArrayList<Object>();
        final Stream<Object> stream = Streams.wrap(iterable);
        assertThrows(EndOfStreamException.class, new Executable() {
            @Override
            public void execute() {
                stream.head();
            }
        });
    }

    @Test public void
    throws_an_exception_when_tail_is_called_on_an_empty_wrapper() {
        final Iterable<Object> iterable = new ArrayList<Object>();
        final Stream<Object> stream = Streams.wrap(iterable);
        assertThrows(EndOfStreamException.class, new Executable() {
            @Override
            public void execute() {
                stream.tail();
            }
        });
    }
}
