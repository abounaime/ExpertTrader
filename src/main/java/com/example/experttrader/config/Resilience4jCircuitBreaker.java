package com.example.experttrader.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "resilience4j.circuitbreaker.instances.api-client")
@Data
public class Resilience4jCircuitBreaker {
    private Boolean registerHealthIndicator;
    private Integer slidingWindowSize;
    private Integer minimumNumberOfCalls;
    private Integer failureRateThreshold;
    private Long waitDurationInOpenState;
    private Integer permittedNumberOfCallsInHalfOpenState;
    private Boolean automaticTransitionFromOpenToHalfOpenEnabled;

    @Bean
    CircuitBreaker circuitBreaker() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .slidingWindowSize(slidingWindowSize)
                .minimumNumberOfCalls(minimumNumberOfCalls)
                .failureRateThreshold(failureRateThreshold)
                .waitDurationInOpenState(Duration.ofMillis(waitDurationInOpenState))
                .permittedNumberOfCallsInHalfOpenState(permittedNumberOfCallsInHalfOpenState)
                .automaticTransitionFromOpenToHalfOpenEnabled(automaticTransitionFromOpenToHalfOpenEnabled)
                .build();
        return CircuitBreaker.of("api-client", circuitBreakerConfig);
    }
}
