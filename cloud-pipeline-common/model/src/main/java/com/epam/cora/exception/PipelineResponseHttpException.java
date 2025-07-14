package com.epam.cora.exception;

public class PipelineResponseHttpException extends PipelineResponseException {

    public PipelineResponseHttpException(final String message) {
        super(message);
    }

    public PipelineResponseHttpException(final Throwable cause) {
        super(cause);
    }

    public PipelineResponseHttpException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
