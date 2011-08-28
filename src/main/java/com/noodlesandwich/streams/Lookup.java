package com.noodlesandwich.streams;

public interface Lookup<K, V> {
    V get(K key);
}
