package com.epam.cora.esa.exception;

public class ElasticClientException extends Exception {

    public ElasticClientException() {
    }

    public ElasticClientException(String message) {
        super(message);
    }

    public ElasticClientException(String message, Throwable exception) {
        super("Exception was thrown while trying to connect to Elasticsearch client. " + message, exception);
    }
}
