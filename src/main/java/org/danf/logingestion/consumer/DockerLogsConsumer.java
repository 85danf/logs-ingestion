package org.danf.logingestion.consumer;

import lombok.extern.slf4j.Slf4j;
import org.danf.logingestion.model.LogLine;
import org.danf.logingestion.repo.LogLineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

import static org.danf.logingestion.common.config.TOPIC;

@Slf4j
@Service
public class DockerLogsConsumer {

    private final AtomicLong idGen = new AtomicLong();
    private final LogLineRepo logLineRepo;

    @Autowired
    public DockerLogsConsumer(LogLineRepo logLineRepo) {
        this.logLineRepo = logLineRepo;
    }

    @KafkaListener(topics = TOPIC, groupId = "docker_logs_consumer")
    public void consume(@Payload String message,
            @Header(name = KafkaHeaders.RECEIVED_MESSAGE_KEY) String containerName,
            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timeStamp) {
        log.info("Consumed log line {} for container {} and timestamp {}", message, containerName, timeStamp);

        var logLine = LogLine.builder()
                .containerName(containerName)
                .content(message)
                .id(idGen.getAndIncrement())
                .build();

        logLineRepo.save(logLine);
    }
}
