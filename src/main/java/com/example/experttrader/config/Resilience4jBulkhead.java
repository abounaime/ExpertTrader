package com.example.experttrader.config;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "resilience4j.bulkhead")
@Data
public class Resilience4jBulkhead  {
    private String name;
    private Integer maxConcurrentCalls;
    private Long maxWaitDuration;

    @Bean
    public Bulkhead bulkhead() {
        BulkheadConfig bulkheadConfig = BulkheadConfig.custom()
                .maxConcurrentCalls(maxConcurrentCalls)
                .maxWaitDuration(Duration.ofMillis(maxWaitDuration))
                .build();
        return Bulkhead.of(name, bulkheadConfig);
    }
}
