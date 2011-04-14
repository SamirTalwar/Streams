package com.noodlesandwich.streams.functions;

public interface ZipWithFunction<A, B, T> {
    T apply(A a, B b);
}
