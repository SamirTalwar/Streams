package com.noodlesandwich.streams.functions;

import org.junit.Test;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.testutils.ThrowingIterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public final class UniqueTest {
    @Test public void
    is_lazy() {
        Stream<Object> stream = Stream.wrap(new ThrowingIterator());
        stream.unique();
    }

    @Test public void
    makes_no_changes_to_an_already_unique_stream() {
        Stream<Integer> stream = Stream.of(7, 2, 5, 9, 4);
        assertThat(stream.unique(), contains(7, 2, 5, 9, 4));
    }

    @Test public void
    strips_duplicates_out_of_a_stream() {
        Stream<Integer> stream = Stream.of(7, 2, 5, 7, 7, 9, 4, 5, 2);
        assertThat(stream.unique(), contains(7, 2, 5, 9, 4));
    }

    @Test public void
    is_repeatable() {
        Stream<Integer> uniqueStream = Stream.of(7, 2, 5, 7, 7, 9, 4, 5, 2).unique();
        assertThat(uniqueStream, contains(7, 2, 5, 9, 4));
        assertThat(uniqueStream, contains(7, 2, 5, 9, 4));
    }
}
