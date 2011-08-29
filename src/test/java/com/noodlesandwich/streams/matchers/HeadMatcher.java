package com.noodlesandwich.streams.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.noodlesandwich.streams.Stream;

public final class HeadMatcher<T> extends TypeSafeDiagnosingMatcher<Stream<T>> {
    private final T head;

    private HeadMatcher(final T head) {
        this.head = head;
    }

    public static <T> HeadMatcher<T> has_a_head_of(final T head) {
        return new HeadMatcher<T>(head);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("has a head of ").appendValue(head);
    }

    @Override
    protected boolean matchesSafely(final Stream<T> stream, final Description mismatchDescription) {
        mismatchDescription.appendText("the stream had a head of ").appendValue(stream.head());
        return head.equals(stream.head());
    }
}
