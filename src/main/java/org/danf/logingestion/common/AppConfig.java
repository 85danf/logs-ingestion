package org.danf.logingestion.common;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.ExitCodeExceptionMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Slf4j
@Configuration
@EnableKafka
@EnableElasticsearchRepositories(basePackages = {"org.danf.logingestion.repo.elastic"})
public class AppConfig {

    /**
     * Catches exceptions and maps them to a non-zero return code so that the pod running this app shows as failed on errors.
     */
    @Bean
    public ExitCodeExceptionMapper exitCodeExceptionMapper() {
        return exception -> {
            log.error("Error running migrations:", exception);
            return 1;
        };
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") // configured in application.yaml
    @Bean
    public KafkaTemplate<String, String> stringTemplate(ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public RestHighLevelClient elasticClient() {
        var config = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();
        return RestClients.create(config).rest();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        var template = new ElasticsearchRestTemplate(elasticClient());
        return template;
    }
}
