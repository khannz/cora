package com.epam.cora.esa.service;

import java.time.LocalDateTime;

public interface ElasticsearchSynchronizer {

    String DOC_TYPE_FIELD = "doc_type";
    void synchronize(LocalDateTime lastSyncTime, LocalDateTime syncStart);
}
