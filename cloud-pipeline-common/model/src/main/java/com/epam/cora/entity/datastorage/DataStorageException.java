package com.epam.cora.entity.datastorage;

public class DataStorageException extends RuntimeException {

    public DataStorageException(String message) {
        super(message);
    }

    public DataStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataStorageException(Throwable cause) {
        super(cause);
    }
}
