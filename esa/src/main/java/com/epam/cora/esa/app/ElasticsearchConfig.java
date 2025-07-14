package com.epam.cora.esa.app;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath*:dao/es-*.xml"})
public class ElasticsearchConfig {

    @Value("${elasticsearch.client.url:#{null}}")
    private String elasticsearchUrl;
    @Value("${elasticsearch.client.port:9200}")
    private int elasticsearchPort;
    @Value("${elasticsearch.client.scheme:http}")
    private String elasticsearchScheme;

    @Bean
    public RestHighLevelClient elasticsearchClient() {
        return new RestHighLevelClient(getRestClientBuilder());
    }

    @Bean
    public RestClient lowLevelClient() {
        return getRestClientBuilder().build();
    }

    private RestClientBuilder getRestClientBuilder() {
        return RestClient.builder(new HttpHost(elasticsearchUrl, elasticsearchPort, elasticsearchScheme));
    }
}
