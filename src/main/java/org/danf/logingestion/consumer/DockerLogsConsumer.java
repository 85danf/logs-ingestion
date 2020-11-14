package org.danf.logingestion.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static org.danf.logingestion.common.config.TOPIC;

@Slf4j
@Service
public class DockerLogsConsumer {


    @KafkaListener(topics = TOPIC, groupId = "docker_logs_consumer")
    public void consume(String message) {
        log.info("Consumed message {}", message);
    }

}
