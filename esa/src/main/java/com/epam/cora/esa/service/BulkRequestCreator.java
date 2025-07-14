package com.epam.cora.esa.service;

import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkResponse;

import java.util.List;

@FunctionalInterface
public interface BulkRequestCreator {
    BulkResponse sendRequest(List<? extends DocWriteRequest> requests);
}
