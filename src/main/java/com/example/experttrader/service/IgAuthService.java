package com.example.experttrader.service;

import com.example.experttrader.config.IgApiProperties;
import com.example.experttrader.dto.AuthenticationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
public class IgAuthService {
    private final static Logger log = LoggerFactory.getLogger(IgAuthService.class);
    private final IgApiProperties igApiProperties;
    private final WebClient webClient;
    
    public IgAuthService(IgApiProperties igApiProperties,
                         WebClient.Builder webClientBuilder) {
        this.igApiProperties = igApiProperties;
        this.webClient = webClientBuilder
                .baseUrl(igApiProperties.getBaseUrl())
                .build();
    }

    public Mono<String> authenticate(){
        var request = createAuthenticationRequest();

        return webClient.post()
                .uri("/session")
                .headers(this::setHeaders)
                .bodyValue(request)
                .retrieve()
                .onStatus(statusCode -> statusCode.is5xxServerError()||
                        statusCode.is4xxClientError(),
                        clientResponse -> Mono.error(
                                new RuntimeException("Authentication Failed "+
                                        "with status "+clientResponse.statusCode())
                        ))
                .toEntity(String.class)
                .map(objectResponseEntity ->
                        extractSecurityToken(objectResponseEntity.getHeaders()
                                .getFirst("X-SECURITY-TOKEN")));
    }



    private void setHeaders(HttpHeaders headers) {
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.set("X-IG-API-KEY", igApiProperties.getKey());
    }

    private AuthenticationRequest createAuthenticationRequest() {
        return new AuthenticationRequest(igApiProperties.getUsername(),
                igApiProperties.getPassword());
    }

    private String extractSecurityToken(String token) {
        if (token == null || token.isEmpty()){
            throw new RuntimeException("Missing X-SECURITY-TOKEN in response");
        }
        log.info("Authentication successful, obtained security token");
        return token;
    }
}

