package org.danf.logingestion.app;

import lombok.extern.slf4j.Slf4j;
import org.danf.logingestion.producer.DockerLogsProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DockerLogsIngestor implements ApplicationRunner {

    private final DockerLogsProducer producer;

    @Autowired
    public DockerLogsIngestor(DockerLogsProducer producer) {
        this.producer = producer;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        producer.start("zookeeper");
        producer.start("kafka1");
        producer.start("kafdrop");
        producer.start("elastic1");
        producer.start("kibana");
    }
}
