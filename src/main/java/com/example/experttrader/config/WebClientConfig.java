package com.example.experttrader.config;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    private static final Logger logger = LoggerFactory.getLogger(WebClientConfig.class);

    private final Resilience4jCircuitBreaker resilience4jCircuitBreaker;
    private final Resilience4jBulkhead resilience4jBulkhead;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final IgApiProperties igApiProperties;

    public WebClientConfig(Resilience4jCircuitBreaker resilience4jCircuitBreaker,
                           Resilience4jBulkhead resilience4jBulkhead,
                           TokenAuthenticationFilter tokenAuthenticationFilter,
                           IgApiProperties igApiProperties) {
        this.resilience4jCircuitBreaker = resilience4jCircuitBreaker;
        this.resilience4jBulkhead = resilience4jBulkhead;
        this.tokenAuthenticationFilter = tokenAuthenticationFilter;
        this.igApiProperties = igApiProperties;
    }

    @Bean
    public WebClient webClient() {
        validateBaseUrl();
        return  createWebClientBuilder()
                .filter(createTokenAuthenticationFilter())
                .build();
    }
    @Bean
    public WebClient authWebClient() {
        validateBaseUrl();
        return  createWebClientBuilder().build();
    }
    private WebClient.Builder createWebClientBuilder() {
        return WebClient.builder()
                .baseUrl(igApiProperties.getBaseurl())
                .filter(createCircuitBreakerFilter())
                .filter(createBulkheadFilter());
    }
    private  void validateBaseUrl() {
        var baseUrl = igApiProperties.getBaseurl();
        if (baseUrl == null || baseUrl.trim().isEmpty()) {
            logger.error("baseUrl is null or empty");
            throw new IllegalArgumentException("baseUrl is null or empty");
        }
        if (! baseUrl.startsWith("https://")){
            logger.error("Insecure base url detected, Use Https instead");
            throw new IllegalArgumentException("Insecure base url detected, Use Https instead");
        }
        logger.info("Creating web client");
    }

    private ExchangeFilterFunction createBulkheadFilter() {
        return (request, next) -> Bulkhead.decorateSupplier(
                        resilience4jBulkhead.bulkhead(),
                        () -> next.exchange(request))
                .get();
    }

    private ExchangeFilterFunction createCircuitBreakerFilter() {
        return (request, next) -> CircuitBreaker.decorateSupplier(
                resilience4jCircuitBreaker.circuitBreaker(),
                () -> next.exchange(request)
                ).get();
    }

    private ExchangeFilterFunction createTokenAuthenticationFilter() {
        return (request, next) ->
                tokenAuthenticationFilter.addAuthHeader(request)
                        .flatMap(next::exchange);
    }
}

