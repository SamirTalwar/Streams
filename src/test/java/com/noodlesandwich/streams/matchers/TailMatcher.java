package com.noodlesandwich.streams.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.noodlesandwich.streams.Stream;

public final class TailMatcher<T> extends TypeSafeDiagnosingMatcher<Stream<T>> {
    private final Stream<T> tail;

    private TailMatcher(Stream<T> tail) {
        this.tail = tail;
    }

    public static <T> TailMatcher<T> has_a_tail_of(Stream<T> tail) {
        return new TailMatcher<T>(tail);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has a tail of ").appendValueList("[", ", ", "]", tail);
    }

    @Override
    protected boolean matchesSafely(Stream<T> stream, Description mismatchDescription) {
        mismatchDescription.appendText("the stream had a tail of ").appendValue(stream.tail());
        return tail.equals(stream.tail());
    }
}
