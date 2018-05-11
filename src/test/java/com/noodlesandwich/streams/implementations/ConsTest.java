package com.noodlesandwich.streams.implementations;

import org.junit.jupiter.api.Test;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import static com.noodlesandwich.streams.matchers.HeadMatcher.has_a_head_of;
import static com.noodlesandwich.streams.matchers.NilMatcher.nil;
import static com.noodlesandwich.streams.matchers.TailMatcher.has_a_tail_of;

public final class ConsTest {
    @Test public void
    is_not_nil() {
        assertThat(Streams.cons(new Object(), Streams.nil()), is(not(nil())));
    }

    @Test public void
    has_a_head_of_whatever_was_specified() {
        final Object head = new Object();
        assertThat(Streams.cons(head, Streams.nil()), has_a_head_of(head));
    }

    @Test public void
    has_a_tail_of_whatever_was_specified() {
        final Stream<Object> tail = Streams.cons(new Object(), Streams.nil());
        assertThat(Streams.cons(new Object(), tail), has_a_tail_of(tail));
    }

    @Test public void
    the_head_does_not_change() {
        final Stream<Object> stream = Streams.cons(new Object(), Streams.nil());
        final Object head = stream.head();
        assertThat(stream, has_a_head_of(head));
    }

    @Test public void
    the_tail_does_not_change() {
        final Stream<Object> stream = Streams.cons(new Object(), Streams.nil());
        final Stream<Object> tail = stream.tail();
        assertThat(stream, has_a_tail_of(tail));
    }

    @Test public void
    iterates() {
        assertThat(Streams.cons(1, Streams.cons(2, Streams.cons(3, Streams.<Integer>nil()))), contains(1, 2, 3));
    }
}
