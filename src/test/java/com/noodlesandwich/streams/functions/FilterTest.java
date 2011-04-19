package com.noodlesandwich.streams.functions;

import org.junit.Test;

import com.google.common.base.Predicate;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.testutils.ThrowingIterator;

import static com.noodlesandwich.streams.matchers.NilMatcher.nil;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public final class FilterTest {
    @Test public void
    is_lazy() {
        Stream<Object> stream = Stream.wrap(new ThrowingIterator());
        stream.filter(null);
    }

    @Test public void
    filters_a_stream_with_a_predicate() {
        Stream<Integer> stream = Stream.of(1, 3, 2, 4, 7, 8, 9, 6, 5);
        assertThat(stream.filter(isEven()), contains(2, 4, 8, 6));
    }

    @Test public void
    filters_nil_to_nil() {
        assertThat(Stream.nil().filter(null), is(nil()));
    }

    @Test public void
    is_repeatable() {
        Stream<Integer> filteredStream = Stream.of(1, 3, 2, 4, 7, 8, 9, 6, 5).filter(isEven());
        assertThat(filteredStream, contains(2, 4, 8, 6));
        assertThat(filteredStream, contains(2, 4, 8, 6));
    }

    private static Predicate<Integer> isEven() {
        return new Predicate<Integer>() {
            @Override
            public boolean apply(Integer input) {
                return input % 2 == 0;
            }
        };
    }
}
