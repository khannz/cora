package com.epam.cora.esa.app;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath*:dao/es-*.xml"})
public class OpenSearchConfig {

    @Value("${opensearch.client.url:#{null}}")
    private String openSearchUrl;

    @Value("${opensearch.client.port:9200}")
    private int openSearchPort;

    @Value("${opensearch.client.scheme:http}")
    private String openSearchScheme;

    @Value("${opensearch.client.username:#{null}}")
    private String username;

    @Value("${opensearch.client.password:#{null}}")
    private String password;

    @Bean
    public RestHighLevelClient openSearchClient() {
        return new RestHighLevelClient(getRestClientBuilder());
    }

    @Bean
    public RestClient lowLevelClient() {
        return getRestClientBuilder().build();
    }

    private RestClientBuilder getRestClientBuilder() {
        RestClientBuilder builder = RestClient.builder(new HttpHost(openSearchUrl, openSearchPort, openSearchScheme));
        if (username != null && !username.isEmpty()) {
            BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
            builder.setHttpClientConfigCallback(httpClientBuilder ->
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        }
        return builder;
    }
}
