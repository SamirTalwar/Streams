package com.noodlesandwich.streams.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.noodlesandwich.streams.Stream;

public final class TailMatcher<T> extends TypeSafeDiagnosingMatcher<Stream<T>> {
    private final Stream<T> tail;

    private TailMatcher(final Stream<T> tail) {
        this.tail = tail;
    }

    public static <T> TailMatcher<T> has_a_tail_of(final Stream<T> tail) {
        return new TailMatcher<>(tail);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("has a tail of ").appendValueList("[", ", ", "]", tail);
    }

    @Override
    protected boolean matchesSafely(final Stream<T> stream, final Description mismatchDescription) {
        mismatchDescription.appendText("the stream had a tail of ").appendValue(stream.tail());
        return tail.equals(stream.tail());
    }
}
