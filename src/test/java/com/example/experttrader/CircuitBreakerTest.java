package com.example.experttrader;

import com.example.experttrader.config.Resilience4jCircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootTest
public class CircuitBreakerTest {

    @Autowired
    private WebClient webClient;
    @Autowired
    private CircuitBreaker circuitBreaker;
    @Test
    void testCircuitBreakerOpensAfterFailures() throws InterruptedException {
        Assertions.assertNotEquals(circuitBreaker, "CircuitBreaker should be injected");
        for (int i = 0; i < 10; i++) {
            webClient.get()
                    .uri("http://localhost:8080/api/ig-auth/token/invalid")
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new RuntimeException()))
                    .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException()))
                    .bodyToMono(String.class)
                    .doOnError(e -> System.out.println("request failed: " + e.getMessage()))
                    .onErrorResume(e -> Mono.empty())
                    .block();
        }
        Assertions.assertEquals(CircuitBreaker.State.OPEN, circuitBreaker.getState(),
                "Circuit breaker should be OPEN after the failures");
    }
}
