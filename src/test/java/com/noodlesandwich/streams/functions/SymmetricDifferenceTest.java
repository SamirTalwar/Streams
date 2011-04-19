package com.noodlesandwich.streams.functions;

import org.junit.Test;

import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.matchers.NilMatcher;
import com.noodlesandwich.streams.testutils.ThrowingIterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public final class SymmetricDifferenceTest {
    @Test public void
    is_lazy() {
        Stream<Object> stream = Stream.wrap(new ThrowingIterator());
        stream.symmetricDifference(stream);
    }

    @Test public void
    a_symmetric_difference_of_anything_with_nil_results_in_the_first_stream() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        assertThat(stream.symmetricDifference(Stream.<Integer>nil()), contains(1, 2, 3));
    }

    @Test public void
    a_symmetric_difference_of_nil_with_anything_results_in_the_second_stream() {
        Stream<Integer> stream = Stream.of(1, 2, 3);
        assertThat(Stream.<Integer>nil().symmetricDifference(stream), contains(1, 2, 3));
    }

    @Test public void
    the_symmetric_difference_of_two_disjunct_streams_results_in_a_concatenation() {
        Stream<Integer> streamOne = Stream.of(1, 2, 3);
        Stream<Integer> streamTwo = Stream.of(6, 5, 4);
        assertThat(streamOne.symmetricDifference(streamTwo), contains(1, 2, 3, 6, 5, 4));
    }

    @Test public void
    removes_items_in_both_streams() {
        Stream<Integer> streamOne = Stream.of(1, 2, 3);
        Stream<Integer> streamTwo = Stream.of(3, 4);
        assertThat(streamOne.symmetricDifference(streamTwo), contains(1, 2, 4));
    }

    @Test public void
    removes_duplicates() {
        Stream<Integer> streamOne = Stream.of(1, 2, 1, 3);
        Stream<Integer> streamTwo = Stream.of(3, 4, 4);
        assertThat(streamOne.symmetricDifference(streamTwo), contains(1, 2, 4));
    }

    @Test public void
    the_symmetric_difference_of_two_identical_streams_is_nil() {
        Stream<Integer> streamOne = Stream.of(1, 2, 3);
        Stream<Integer> streamTwo = Stream.of(1, 2, 3);
        assertThat(streamOne.symmetricDifference(streamTwo), is(NilMatcher.<Integer>nil()));
    }

    @Test public void
    the_symmetric_difference_of_two_streams_containing_the_same_elements_is_nil() {
        Stream<Integer> streamOne = Stream.of(1, 2, 3);
        Stream<Integer> streamTwo = Stream.of(2, 3, 1);
        assertThat(streamOne.symmetricDifference(streamTwo), is(NilMatcher.<Integer>nil()));
    }

    @Test public void
    is_repeatable() {
        Stream<Integer> differentiatedStream = Stream.of(1, 2, 3).symmetricDifference(Stream.of(3, 4));
        assertThat(differentiatedStream, contains(1, 2, 4));
        assertThat(differentiatedStream, contains(1, 2, 4));
    }
}
