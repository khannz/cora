package com.epam.cora.rest;

public enum ResultStatus {

    /**
     * Request was successfully processed
     */
    OK,

    /**
     * Tells some information to the client
     */
    INFO,

    /**
     * Some warning information
     */
    WARN,

    /**
     * An error occurred while request was processed
     */
    ERROR;
}
