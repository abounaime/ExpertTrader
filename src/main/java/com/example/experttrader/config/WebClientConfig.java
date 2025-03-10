package com.example.experttrader.config;

import com.example.experttrader.service.IgAuthService;
import com.example.experttrader.service.TokenStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    private static final Logger logger = LoggerFactory.getLogger(WebClientConfig.class);
    @Bean
    public WebClient webClient(TokenStorageService tokenStorageService, IgApiProperties igApiProperties) {
            checkBaseUrl(igApiProperties);
            return WebClient.builder()
                    .baseUrl(igApiProperties.getBaseurl())
                    .filter(new TokenAuthenticationFilter(tokenStorageService).filter())  // Add token filter
                    .build();

    }
    @Bean
    public WebClient authWebClient(IgApiProperties igApiProperties) {
            checkBaseUrl(igApiProperties);
            return WebClient.builder()
                    .baseUrl(igApiProperties.getBaseurl())
                    .build();
    }

    private static void checkBaseUrl(IgApiProperties igApiProperties) {
        var baseUrl = igApiProperties.getBaseurl();
        if (baseUrl == null || baseUrl.trim().isEmpty()) {
            logger.error("baseUrl is null or empty");
            throw new IllegalArgumentException("baseUrl is null or empty");
        }
        logger.info("Creating web client with base Url: {}", baseUrl);
    }
}

