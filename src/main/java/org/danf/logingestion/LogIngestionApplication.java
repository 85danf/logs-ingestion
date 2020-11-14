package org.danf.logingestion;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class LogIngestionApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(LogIngestionApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
