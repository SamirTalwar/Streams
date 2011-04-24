package com.noodlesandwich.streams.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;

public class Comparators {

	public static final Comparator<Integer> INTEGER = new Comparator<Integer>() {
		@Override
		public int compare(Integer o1, Integer o2) {
			return o1.compareTo(o2);
		}
	};

	public static final Comparator<Long> LONG = new Comparator<Long>(){
		@Override
		public int compare(Long o1, Long o2) {
			return o1.compareTo(o2);
		}
	};
	
	public static final Comparator<BigInteger> BIG_INTEGER = new Comparator<BigInteger>() {
		@Override
		public int compare(BigInteger o1, BigInteger o2) {
			return o1.compareTo(o2);
		}
	};
	
	public static final Comparator<Double> DOUBLE = new Comparator<Double>(){
		@Override
		public int compare(Double o1, Double o2) {
			return o1.compareTo(o2);
		}
	};
	
	public static final Comparator<Float> FLOAT = new Comparator<Float>() {
		@Override
		public int compare(Float o1, Float o2) {
			return o1.compareTo(o2);
		}
	};
	public static final Comparator<BigDecimal> BIG_DECIMAL = new Comparator<BigDecimal>() {
		@Override
		public int compare(BigDecimal o1, BigDecimal o2) {
			return o1.compareTo(o2);
		}
	};
	public static final Comparator<Character> CHAR = new Comparator<Character>(){
		@Override
		public int compare(Character o1, Character o2) {
			return o1.compareTo(o2);
		}
	};
	public static final Comparator<String> String = new Comparator<String>(){
		@Override
		public int compare(String o1, String o2) {
			return o1.compareTo(o2);
		}
	};
}
