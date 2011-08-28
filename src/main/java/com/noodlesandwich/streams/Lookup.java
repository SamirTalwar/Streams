package com.noodlesandwich.streams;

/**
 * A Lookup is a lazy object that allows you to retrieve objects by key. Unlike a {@link java.util.Map}, the contract
 * does not expect the lookup to know which objects it contains, only that such an object might exist. Lookups are not
 * data stores; a user cannot place information into one in order to pull it out later. Instead, they are a view upon
 * an object or set of objects. For an example of how they might be used, experiment with the
 * {@link Stream#groupBy Stream&lt;T&gt;#groupBy} method.
 */
public interface Lookup<K, V> {
    /**
     * Looks up an object using the key provided. This object will never be <code>null</code>. The object may be
     * retrieved from a store or calculated as part of the retrieval operation.
     */
    V get(K key);
}
