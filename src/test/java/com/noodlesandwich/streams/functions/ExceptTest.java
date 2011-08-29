package com.noodlesandwich.streams.functions;

import org.junit.Test;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;
import com.noodlesandwich.streams.matchers.NilMatcher;
import com.noodlesandwich.streams.testutils.ThrowingIterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public final class ExceptTest {
    @Test public void
    is_lazy() {
        final Stream<Object> stream = Streams.wrap(new ThrowingIterator());
        stream.except(null);
    }

    @Test public void
    anything_except_nil_results_in_no_change() {
        final Stream<Integer> stream = Streams.of(1, 2, 3);
        assertThat(stream.except(Streams.<Integer>nil()), contains(1, 2, 3));
    }

    @Test public void
    a_stream_except_another_disjunct_stream_results_in_no_change() {
        final Stream<Integer> streamOne = Streams.of(1, 2, 3);
        final Stream<Integer> streamTwo = Streams.of(4, 5, 6);
        assertThat(streamOne.except(streamTwo), contains(1, 2, 3));
    }

    @Test public void
    removes_items_in_both_streams() {
        final Stream<Integer> streamOne = Streams.of(1, 2, 3);
        final Stream<Integer> streamTwo = Streams.of(3, 4);
        assertThat(streamOne.except(streamTwo), contains(1, 2));
    }

    @Test public void
    does_not_remove_duplicates() {
        final Stream<Integer> streamOne = Streams.of(1, 2, 3, 2);
        final Stream<Integer> streamTwo = Streams.of(3, 4);
        assertThat(streamOne.except(streamTwo), contains(1, 2, 2));
    }

    @Test public void
    excepting_a_superset_results_in_nil() {
        final Stream<Integer> streamOne = Streams.of(1, 2, 3);
        final Stream<Integer> streamTwo = Streams.of(4, 3, 2, 1);
        assertThat(streamOne.except(streamTwo), is(NilMatcher.<Integer>nil()));
    }

    @Test public void
    is_repeatable() {
        final Stream<Integer> exceptedStream = Streams.of(1, 2, 3).except(Streams.of(3, 4));
        assertThat(exceptedStream, contains(1, 2));
        assertThat(exceptedStream, contains(1, 2));
    }
}
