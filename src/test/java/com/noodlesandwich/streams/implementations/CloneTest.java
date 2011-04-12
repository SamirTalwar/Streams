package com.noodlesandwich.streams.implementations;

import java.util.Arrays;

import org.junit.Test;

import com.noodlesandwich.streams.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class CloneTest {
    @Test public void
    clones_an_existing_iterable() {
        Iterable<Integer> iterable = Arrays.asList(1, 2, 3);
        assertThat(Stream.from(iterable), contains(1, 2, 3));
    }
}
