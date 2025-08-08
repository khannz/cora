package com.epam.cora.esa.service;

import org.opensearch.action.DocWriteRequest;
import org.opensearch.action.bulk.BulkResponse;

import java.util.List;

@FunctionalInterface
public interface BulkRequestCreator {
    BulkResponse sendRequest(List<? extends DocWriteRequest> requests);
}
