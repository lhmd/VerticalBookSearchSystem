package com.bookwise.sembackend;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.List;

@Configuration
public class ElasticSearchClientConfig {
    @Bean
    public ElasticsearchClient docqaElasticsearchClient() {
        HttpHost[] httpHosts1 = List.of(new HttpHost("10.73.103.130", 9200)).toArray(new HttpHost[0]);
        RestClient client = RestClient
                .builder(httpHosts1)
                .build();

        ElasticsearchTransport transport = new RestClientTransport(client, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }
}