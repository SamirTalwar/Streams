package com.noodlesandwich.streams.functions;

import java.util.Comparator;

import com.google.common.base.Predicate;
import com.noodlesandwich.streams.Pair;
import com.noodlesandwich.streams.Stream;

public class Pivot{

	public static <T> Pair<Stream<T>, Stream<T>> apply(Stream<T> input, T pivotVal, Comparator<T> comparator){
		Stream<T> lessThanStream = input.filter(lessThan(pivotVal, comparator));
		Stream<T> greaterThanStream = input.filter(greaterThan(pivotVal, comparator));
		return new Pair<Stream<T>, Stream<T>>(lessThanStream, greaterThanStream);
	}
	
	private static <T> Predicate<T> greaterThan(final T pivotVal, final Comparator<T> comparator){
		return new Predicate<T>() {
			@Override
			public boolean apply(T input) {
				return comparator.compare(input, pivotVal) > 0;
			}
		};
	}
	
	private static <T> Predicate<T> lessThan(final T pivotVal, final Comparator<T> comparator){
		return new Predicate<T>() {
			@Override
			public boolean apply(T input) {
				return comparator.compare(input, pivotVal) < 0;
			}
		};
	}
}
