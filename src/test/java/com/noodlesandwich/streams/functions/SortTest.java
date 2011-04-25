package com.noodlesandwich.streams.functions;

import java.util.Comparator;

import org.junit.Test;

import com.google.common.base.Functions;
import com.google.common.collect.Ordering;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.testutils.ThrowingIterator;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class SortTest {
    @Test public void
    is_lazy() {
        Stream<Object> stream = Stream.wrap(new ThrowingIterator());
        stream.sort(null);
    }

    @Test public void
    does_nothing_to_an_empty_list() {
        assertThat(Stream.nil().sort(Ordering.arbitrary()), is(nil()));
    }

    @Test public void
    sorts_objects_according_to_a_comparator() {
        Stream<Integer> stream = Stream.of(7, 5, 8, 1, 2, 9, 3, 6, 4);
        assertThat(stream.sort(Ordering.natural()), contains(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    @Test public void
    is_stable() {
        Stream<Integer> stream = Stream.of(7, 5, 8, 1, 2, 9, 3, 6, 4);
        assertThat(stream.sort(evensAreOdds()), contains(1, 2, 3, 5, 4, 7, 6, 8, 9));
    }

    @Test public void
    sorts_without_a_comparator_if_the_underlying_type_implements_Comparable() {
        Stream<Integer> stream = Stream.of(7, 5, 8, 1, 2, 9, 3, 6, 4);
        assertThat(stream.sort(), contains(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    @Test(expected=ClassCastException.class) public void
    throws_a_ClassCastException_if_the_underlying_type_does_not_implement_Comparable() {
        Stream<Object> stream = Stream.of(new Object(), new Object(), new Object());
        stream.sort().head();
    }

    @Test public void
    sorts_by_a_mapping() {
        Thing a = new Thing("a");
        Thing b = new Thing("b");
        Thing c = new Thing("c");
        Thing d = new Thing("d");

        Stream<Thing> stream = Stream.of(d, a, c, b);
        assertThat(stream.sortBy(Functions.identity(), Ordering.usingToString()), contains(a, b, c, d));
    }

    private static Comparator<? super Integer> evensAreOdds() {
        return new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return Integer.valueOf(a - a % 2).compareTo(b - b % 2);
            }
        };
    }

    private static class Thing {
        private final String string;

        public Thing(String string) {
            this.string = string;
        }

        @Override
        public String toString() {
            return string;
        }
    }
}
