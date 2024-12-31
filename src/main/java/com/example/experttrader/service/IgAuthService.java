package com.example.experttrader.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    @Value("${ig.api.base-url}")
    private String baseUrl;
    @Value("${ig.api.username}")
    private String username;
    @Value("${ig.api.password}")
    private String password;
    @Value("${ig.api.key}")
    private String apiKey;


    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    public IgAuthService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<String> authenticate(){
        AuthenticationRequest request = new AuthenticationRequest(username, password);
        return webClient.post()
                .uri(baseUrl+"/session")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .header("X-IG-API-KEY", apiKey)
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

    private static class AuthenticationRequest{
        private String username;
        private String password;
        public AuthenticationRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
        public String getUsername(){
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}

