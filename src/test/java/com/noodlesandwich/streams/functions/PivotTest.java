package com.noodlesandwich.streams.functions;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;

import com.noodlesandwich.streams.Pair;
import com.noodlesandwich.streams.Stream;
import com.noodlesandwich.streams.utils.Comparators;

public class PivotTest {

	@Test public void
	single_element_pivot(){
		Stream<Integer> stream = Stream.<Integer>of(1);
		Pair<Stream<Integer>, Stream<Integer>> pair = stream.pivot(1, Comparators.INTEGER);
		assertTrue(pair.first().isEmpty());
		assertTrue(pair.second().isEmpty());
	}
	
	@Test public void
	zero_element_pivot(){
		Stream<Long> stream = Stream.wrap(Collections.<Long>emptyList());
		Pair<Stream<Long>, Stream<Long>> pair = stream.pivot(10L, Comparators.LONG);
		assertTrue(pair.first().isEmpty());
		assertTrue(pair.second().isEmpty());
	}
	
	@Test public void 
	double_stream_pivot(){

		Stream<Double> stream = Stream.<Double>of(14.0, 2.0, 13.0, 1.0, 0.0, -5.0, 20.0);
		Pair<Stream<Double>, Stream<Double>> pair = stream.pivot(13.0, Comparators.DOUBLE);
		
		assertTrue(pair.first().size() == 4);
		assertThat(pair.first(), contains(2.0, 1.0, 0.0, -5.0));
		
		assertTrue(pair.second().size() == 2);
		assertThat(pair.second(), contains(14.0, 20.0));
	}
}
