package com.noodlesandwich.streams;

/**
 * <p>Thrown when the user attempts to access past the end of a stream.</p>
 *
 * <p>Can be thrown by a function used as an argument to the
 * {@link Stream#generate Stream.generate(Function&lt;T, T&gt; function, T start)} constructor to signal the end of the
 * stream.</p>
 */
public final class EndOfStreamException extends RuntimeException {
    private static final long serialVersionUID = 6349624304997685948L;

    public EndOfStreamException() {
        super("End of stream");
    }
}
