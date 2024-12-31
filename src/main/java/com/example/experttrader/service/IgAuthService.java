package com.example.experttrader.service;

import com.example.experttrader.config.IgApiProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class IgAuthService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final IgApiProperties igApiProperties;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    public IgAuthService(IgApiProperties igApiProperties, WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.igApiProperties = igApiProperties;
        this.objectMapper = objectMapper;
        this.webClient = webClientBuilder.baseUrl(igApiProperties.getBaseUrl()).build();
    }

    public Mono<String> authenticate(){
        AuthenticationRequest request = new AuthenticationRequest(igApiProperties.getUsername(),
                igApiProperties.getPassword());

        return webClient.post()
                .uri("/session")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header("X-IG-API-KEY", igApiProperties.getKey())
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() ||
                        status.is5xxServerError(),
                        response ->
                                Mono.error(
                                        new RuntimeException("Authentication failed " +
                                                "with status: "
                                                + response.statusCode()))
                )
                .toEntity(String.class)
                .map(response -> extractSecurityToken(
                        response.getHeaders().getFirst("X-SECURITY-TOKEN")));
    }

    private String extractSecurityToken(String token) {
        if (token == null || token.isEmpty()){
            throw new RuntimeException("Missing X-SECURITY-TOKEN in response");
        }
        log.info("Authentication successful, obtained security token: {}", token);
        return token;
    }
    @Data
    private static class AuthenticationRequest{
        private String identifier;
        private String password;
        public AuthenticationRequest(String identifier, String password) {
            this.identifier = identifier;
            this.password = password;
        }

    }
}

