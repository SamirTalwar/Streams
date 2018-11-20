package com.noodlesandwich.streams.functions;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import com.google.common.base.Functions;
import com.google.common.collect.Ordering;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.Streams;
import com.noodlesandwich.streams.testutils.ThrowingIterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;

public final class SortTest {
    @Test public void
    is_lazy() {
        final Stream<Object> stream = Streams.wrap(new ThrowingIterator());
        stream.sort(null);
    }

    @Test public void
    does_nothing_to_an_empty_list() {
        assertThat(Streams.nil().sort(Ordering.arbitrary()), is(nil()));
    }

    @Test public void
    sorts_objects_according_to_a_comparator() {
        final Stream<Integer> stream = Streams.of(7, 5, 8, 1, 2, 9, 3, 6, 4);
        assertThat(stream.sort(Ordering.natural()), contains(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    @Test public void
    is_stable() {
        final Stream<Integer> stream = Streams.of(7, 5, 8, 1, 2, 9, 3, 6, 4);
        assertThat(stream.sort(evensAreOdds()), contains(1, 2, 3, 5, 4, 7, 6, 8, 9));
    }

    @Test public void
    sorts_without_a_comparator_if_the_underlying_type_implements_Comparable() {
        final Stream<Integer> stream = Streams.of(7, 5, 8, 1, 2, 9, 3, 6, 4);
        assertThat(stream.sort(), contains(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    @Test public void
    throws_a_ClassCastException_if_the_underlying_type_does_not_implement_Comparable() {
        final Stream<Object> stream = Streams.of(new Object(), new Object(), new Object());
        assertThrows(ClassCastException.class, () -> stream.sort().head());
    }

    @Test public void
    sorts_by_a_mapping() {
        final Thing a = new Thing("a");
        final Thing b = new Thing("b");
        final Thing c = new Thing("c");
        final Thing d = new Thing("d");

        final Stream<Thing> stream = Streams.of(d, a, c, b);
        assertThat(stream.sortBy(Functions.identity(), Ordering.usingToString()), contains(a, b, c, d));
    }

    @Test public void
    sorts_by_a_mapping_without_a_comparator_if_the_underlying_type_of_the_mapping_implements_Comparable() {
        final Thing a = new Thing("a");
        final Thing b = new Thing("b");
        final Thing c = new Thing("c");
        final Thing d = new Thing("d");

        final Stream<Thing> stream = Streams.of(d, a, c, b);
        assertThat(stream.sortBy(Functions.toStringFunction()), contains(a, b, c, d));
    }

    private static Comparator<? super Integer> evensAreOdds() {
        return Comparator.comparingInt((Integer x) -> x - x % 2);
    }

    private static class Thing {
        private final String string;

        public Thing(final String string) {
            this.string = string;
        }

        @Override
        public String toString() {
            return string;
        }
    }
}
