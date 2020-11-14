package org.danf.logingestion.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class DockerLogsProducer {

    private final KafkaTemplate<String, String> template;
    private final ExecutorService threadPool;

    @Autowired
    public DockerLogsProducer(KafkaTemplate<String, String> template) {
        this.template = template;
        this.threadPool = Executors.newCachedThreadPool();
    }


    public void start(String containerName) {
        DockerLogsReader logsProducer = new DockerLogsReader(containerName, template);
        threadPool.submit(logsProducer);
    }

}
