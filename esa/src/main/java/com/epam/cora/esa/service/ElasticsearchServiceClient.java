package com.epam.cora.esa.service;

import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.Scroll;

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
