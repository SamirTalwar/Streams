package com.noodlesandwich.streams;

public class EndOfStreamException extends RuntimeException {
    private static final long serialVersionUID = 6349624304997685948L;

    public EndOfStreamException() {
        super("End of stream");
    }
}
