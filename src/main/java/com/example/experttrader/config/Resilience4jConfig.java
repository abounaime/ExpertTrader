package com.example.experttrader.config;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

public class Resilience4jConfig {
    @Bean
    public Bulkhead bulkhead() {
        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(5)
                .maxWaitDuration(Duration.ofMillis(500))
                .build();
        return Bulkhead.of("igApiBulkhead", config);
    }
}
