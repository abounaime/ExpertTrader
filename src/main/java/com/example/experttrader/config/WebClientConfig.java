package com.example.experttrader.config;

import com.example.experttrader.service.IgAuthService;
import com.example.experttrader.service.TokenStorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient(TokenStorageService tokenStorageService, IgAuthService igAuthService,
                               IgApiProperties igApiProperties){
        return WebClient.builder()
                .baseUrl(igApiProperties.getBaseUrl())
                .filter(new TokenAuthenticationFilter(tokenStorageService, igAuthService).filter())  // Add token filter
                .build();

    }
}
