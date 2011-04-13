package com.noodlesandwich.streams.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;

import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Stream;

import static com.noodlesandwich.streams.matchers.HeadMatcher.has_a_head_of;
import static com.noodlesandwich.streams.matchers.NilMatcher.nil;
import static com.noodlesandwich.streams.matchers.TailMatcher.has_a_tail_of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class WrapperTest {
    @Test public void
    wraps_an_existing_iterable() {
        Iterable<Integer> iterable = Arrays.asList(1, 2, 3);
        assertThat(Stream.wrap(iterable), contains(1, 2, 3));
    }

    @Test public void
    is_nil_when_the_iterable_is_empty() {
        Iterable<Object> iterable = new ArrayList<Object>();
        assertThat(Stream.wrap(iterable), is(nil()));
    }

    @Test public void
    is_not_nil_when_the_iterable_has_items() {
        Iterable<Object> iterable = Arrays.asList(new Object());
        assertThat(Stream.wrap(iterable), is(not(nil())));
    }

    @Test public void
    wraps_an_iterator() {
        Iterator<Integer> iterator = Arrays.asList(1, 2, 3).iterator();
        assertThat(Stream.wrap(iterator), contains(1, 2, 3));
    }

    @Test public void
    the_head_does_not_change() {
        Stream<Integer> stream = Stream.wrap(Arrays.asList(1, 2, 3));
        Integer head = stream.head();
        assertThat(stream, has_a_head_of(head));
    }

    @Test public void
    the_tail_does_not_change() {
        Stream<Integer> stream = Stream.wrap(Arrays.asList(1, 2, 3));
        Stream<Integer> tail = stream.tail();
        assertThat(stream, has_a_tail_of(tail));
    }

    @Test public void
    can_call_tail_before_head() {
        Stream<Integer> stream = Stream.wrap(Arrays.asList(1, 2, 3));
        assertThat(stream.tail(), contains(2, 3));
        assertThat(stream.head(), is(1));
    }

    @Test(expected=EndOfStreamException.class) public void
    throws_an_exception_when_head_is_called_on_an_empty_wrapper() {
        Iterable<Object> iterable = new ArrayList<Object>();
        Stream<Object> stream = Stream.wrap(iterable);
        stream.head();
    }

    @Test(expected=EndOfStreamException.class) public void
    throws_an_exception_when_tail_is_called_on_an_empty_wrapper() {
        Iterable<Object> iterable = new ArrayList<Object>();
        Stream<Object> stream = Stream.wrap(iterable);
        stream.tail();
    }
}
