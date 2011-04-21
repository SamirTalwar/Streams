package com.noodlesandwich.streams;

public abstract class CachedStream<T> extends Stream<T> {
    private boolean determinedIsNil = false;
    private boolean isNil;

    private boolean determinedHead = false;
    private T head;

    private boolean determinedTail = false;
    private Stream<T> tail;

    private final Object lock = new Object();

    @Override
    public boolean isNil() {
        synchronized (lock) {
            if (!determinedIsNil) {
                isNil = determineIsNil();
                determinedIsNil = true;
            }
        }

        return isNil;
    }

    @Override
    public T head() {
        synchronized (lock) {
            if (!determinedHead) {
                head = determineHead();
                determinedHead = true;
            }
        }

        return head;
    }

    @Override
    public Stream<T> tail() {
        synchronized (lock) {
            if (!determinedTail) {
                tail = determineTail();
                determinedTail = true;
            }
        }

        return tail;
    }

    public abstract boolean determineIsNil();

    public abstract T determineHead();

    public abstract Stream<T> determineTail();
}
