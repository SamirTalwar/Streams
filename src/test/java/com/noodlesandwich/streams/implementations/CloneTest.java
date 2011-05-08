package com.noodlesandwich.streams.implementations;

import java.util.Arrays;

import com.noodlesandwich.streams.Streams;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public final class CloneTest {
    @Test public void
    clones_an_existing_iterable() {
        Iterable<Integer> iterable = Arrays.asList(1, 2, 3);
        assertThat(Streams.clone(iterable), contains(1, 2, 3));
    }
}
