package com.epam.cora.exception;

public class PipelineResponseException extends RuntimeException {
    public PipelineResponseException(final String message) {
        super(message);
    }

    public PipelineResponseException(final Throwable cause) {
        super(cause);
    }

    public PipelineResponseException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
