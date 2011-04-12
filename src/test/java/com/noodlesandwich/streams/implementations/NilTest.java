package com.noodlesandwich.streams.implementations;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;

import com.noodlesandwich.streams.EndOfStreamException;
import com.noodlesandwich.streams.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class NilTest {
    @Test public void
    nil_is_nil() {
        assertThat(Stream.nil(), is(nil()));
    }

    @Test(expected=EndOfStreamException.class) public void
    throws_an_exception_if_head_is_called() {
        Stream.nil().head();
    }

    @Test(expected=EndOfStreamException.class) public void
    throws_an_exception_if_tail_is_called() {
        Stream.nil().tail();
    }

    private static <T> Matcher<Stream<T>> nil() {
        return new TypeSafeDiagnosingMatcher<Stream<T>>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("nil");
            }

            @Override
            protected boolean matchesSafely(Stream<T> item, Description mismatchDescription) {
                mismatchDescription.appendText("the stream was not nil");
                return item.isNil();
            }
        };
    }
}
