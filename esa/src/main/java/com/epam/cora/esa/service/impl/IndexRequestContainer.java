package com.epam.cora.esa.service.impl;

import com.epam.cora.esa.service.BulkRequestCreator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class IndexRequestContainer implements AutoCloseable {
    private List<DocWriteRequest> requests;
    private BulkRequestCreator bulkRequestCreator;
    private Integer bulkSize;

    public IndexRequestContainer(BulkRequestCreator bulkRequestCreator, Integer bulkSize) {
        this.requests = new ArrayList<>();
        this.bulkRequestCreator = bulkRequestCreator;
        this.bulkSize = bulkSize;
    }

    public void add(final DocWriteRequest request) {
        requests.add(request);
        if (requests.size() == bulkSize) {
            flush();
        }
    }

    @Override
    public void close() {
        if (CollectionUtils.isEmpty(requests)) {
            return;
        }
        flush();
    }

    private void flush() {
        BulkResponse documents = bulkRequestCreator.sendRequest(requests);
        long successfulRequestsCount = 0L;
        long unsuccessfulRequestsCount = 0L;
        if (documents != null && documents.getItems() != null) {
            for (final BulkItemResponse response : documents.getItems()) {
                if (response.isFailed()) {
                    unsuccessfulRequestsCount += 1;
                } else {
                    successfulRequestsCount += 1;
                }
            }
        }
        if (unsuccessfulRequestsCount == 0) {
            log.info("{} files have been uploaded", successfulRequestsCount);
        } else {
            log.info("{} files have been uploaded and {} files have not been uploaded", successfulRequestsCount, unsuccessfulRequestsCount);
            Arrays.stream(documents.getItems()).filter(BulkItemResponse::isFailed).findFirst().ifPresent(response -> log.debug("One of the files has not been uploaded due to: {}", response.getFailureMessage()));
        }
        requests.clear();
    }
}
