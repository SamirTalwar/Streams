package com.noodlesandwich.streams.functions;

import org.junit.Test;

import com.noodlesandwich.streams.Stream;

import static com.noodlesandwich.streams.matchers.HeadMatcher.has_a_head_of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public final class UniqueTest {
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
    does_not_alter_itself() {
        Stream<Integer> stream = Stream.of(7, 2, 5, 7, 7, 9, 4, 5, 2);
        Stream<Integer> uniqueStream = stream.unique();
        Integer head = uniqueStream.head();
        assertThat(uniqueStream, has_a_head_of(head));
    }
}
