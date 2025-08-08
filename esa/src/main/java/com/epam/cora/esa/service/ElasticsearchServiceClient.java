package com.epam.cora.esa.service;

import org.opensearch.action.DocWriteRequest;
import org.opensearch.action.bulk.BulkResponse;
import org.opensearch.action.search.MultiSearchRequest;
import org.opensearch.action.search.MultiSearchResponse;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.core.search.Scroll;

import java.util.List;

public interface ElasticsearchServiceClient {

    void createIndex(String indexName, String source);
    BulkResponse sendRequests(String indexName, List<? extends DocWriteRequest> docWriteRequests);
    void deleteIndex(String indexName);
    boolean isIndexExists(String indexName);
    void createIndexAlias(String indexName, String indexAlias);
    String getIndexNameByAlias(String alias);
    List<String> findIndices(String pattern);
    SearchResponse search(SearchRequest request);
    SearchResponse nextScrollPage(String scrollId, Scroll scroll);
    MultiSearchResponse search(MultiSearchRequest request);
}
