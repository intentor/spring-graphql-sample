package org.intentor.samples.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * ApiStarter starter.
 */
@SpringBootApplication
@EnableJpaAuditing
public class ApiStarter {
    public static void main(String[] args) {
        SpringApplication.run(ApiStarter.class, args);
    }
}