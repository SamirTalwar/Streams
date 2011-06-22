package com.noodlesandwich.streams.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.noodlesandwich.streams.Stream;

public final class NilMatcher<T> extends TypeSafeDiagnosingMatcher<Stream<T>> {
    private NilMatcher() { }

    public static <T> NilMatcher<T> nil() {
        return new NilMatcher<T>();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("nil");
    }

    @Override
    protected boolean matchesSafely(Stream<T> stream, Description mismatchDescription) {
        mismatchDescription.appendText("the stream was not nil");
        return stream.isEmpty();
    }
}
