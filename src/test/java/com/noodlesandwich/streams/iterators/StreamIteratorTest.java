package com.noodlesandwich.streams.iterators;

import org.junit.Test;

import com.noodlesandwich.streams.Stream;

import static com.noodlesandwich.streams.Stream.nil;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;

public class StreamIteratorTest {
    @Test public void
    stops_immediately_if_the_stream_is_nil() {
        assertThat(nil(), is(emptyIterable()));
    }

    @Test public void
    iterates_over_the_stream() {
        assertThat(Stream.cons(1, Stream.cons(2, Stream.cons(3, Stream.<Integer>nil()))), contains(1, 2, 3));
    }
}
