package com.epam.cora.esa.exception;

public class EntityNotFoundException extends Exception {

    public EntityNotFoundException(final Throwable cause) {
        super(cause);
    }

    public EntityNotFoundException(final String message) {
        super(message);
    }
}
