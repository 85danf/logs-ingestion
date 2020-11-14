package org.danf.logingestion.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ExitCodeExceptionMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
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

}
