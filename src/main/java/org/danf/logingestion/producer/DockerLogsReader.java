package org.danf.logingestion.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.danf.logingestion.common.config.TOPIC;

@Slf4j
public class DockerLogsReader implements Runnable {

    private final ProcessBuilder dockerLogsBuilder;
    private final KafkaTemplate<String, String> template;
    private final String containerName;

    public DockerLogsReader(String containerName, KafkaTemplate<String, String> template) {
        this.template = template;
        this.containerName = containerName;
        dockerLogsBuilder = new ProcessBuilder("docker", "logs", containerName, "-f");
        dockerLogsBuilder.redirectErrorStream(true);
    }

    @Override
    public void run() {
        Process dockerLogs;
        try {
            dockerLogs = dockerLogsBuilder.start();
        } catch (IOException ioe) {
            log.error("Error starting process '{}'", dockerLogsBuilder.command());
            log.error("", ioe);
            return;
        }
        try (InputStreamReader in = new InputStreamReader(dockerLogs.getInputStream());
             BufferedReader reader = new BufferedReader(in)) {
            String line;
            while ((line = reader.readLine()) != null) {
                sendMessage(line);
            }
        } catch (IOException ioe) {
            log.error("Error reading from docker logs input stream: ", ioe);
        }
    }

    private void sendMessage(String message) {
        template.send(TOPIC, containerName, message);
    }
}
