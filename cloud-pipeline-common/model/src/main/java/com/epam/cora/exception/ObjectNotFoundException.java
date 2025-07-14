package com.epam.cora.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(final Throwable cause) {
        super(cause);
    }

    public ObjectNotFoundException(final String message) {
        super(message);
    }
}
