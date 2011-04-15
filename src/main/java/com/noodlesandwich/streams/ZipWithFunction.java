package com.noodlesandwich.streams;

public interface ZipWithFunction<A, B, T> {
    T apply(A a, B b);
}
