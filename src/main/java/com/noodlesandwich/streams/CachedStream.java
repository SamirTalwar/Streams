package com.noodlesandwich.streams;

/**
 * A <code>CachedStream</code> is a stream that caches the results of {@link #isNil()}, {@link #head()} and
 * {@link #tail()} for reasons of mutability and performance. Streams that rely on external factors that can be mutable
 * may return different results when iterated over more than once unless the original values were cached on the first
 * iteration. In addition, storing the values, while increasing memory usage, may result in a large performance benefit
 * if evaluating one or more of the above methods is time-consuming or heavy on the hardware.
 */
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

    /**
     * Called once when the user calls {@link #isNil()}. The result is cached and used for future calls.
     */
    public abstract boolean determineIsNil();

    /**
     * Called once when the user calls {@link #head()}. The result is cached and used for future calls.
     */
    public abstract T determineHead();

    /**
     * Called once when the user calls {@link #tail()}. The result is cached and used for future calls.
     */
    public abstract Stream<T> determineTail();
}
