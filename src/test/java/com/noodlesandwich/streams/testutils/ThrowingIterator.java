package com.noodlesandwich.streams.testutils;

import java.util.Iterator;

public class ThrowingIterator implements Iterator<Object> {
    @Override
    public boolean hasNext() {
        throw new RuntimeException("Bad things happened.");
    }

    @Override
    public Object next() {
        throw new RuntimeException("Bad things happened.");
    }

    @Override
    public void remove() {
        throw new RuntimeException("Bad things happened.");
    }
}
