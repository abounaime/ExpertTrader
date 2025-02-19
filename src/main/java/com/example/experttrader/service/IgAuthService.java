package com.example.experttrader.service;

import com.example.experttrader.config.IgApiProperties;
import com.example.experttrader.dto.AuthenticationRequest;
import com.example.experttrader.dto.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class IgAuthService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final IgApiProperties igApiProperties;
    private final WebClient webClient;
    private final TokenStorageService tokenStorageService;
    
    public IgAuthService(IgApiProperties igApiProperties,
                         WebClient.Builder webClientBuilder,
                         @Lazy TokenStorageService tokenStorageService) {
        this.igApiProperties = igApiProperties;
        this.tokenStorageService = tokenStorageService;
        this.webClient = webClientBuilder
                .baseUrl(igApiProperties.getBaseUrl())
                .build();
    }


    public Mono<LoginResponse> authenticate(){
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
                .toEntity(LoginResponse.class)
                .doOnNext(response -> this.handleTokens(
                        response.getHeaders().getFirst("X-SECURITY-TOKEN"),
                        response.getHeaders().getFirst("CST")) )
                .map(ResponseEntity::getBody);
    }



    private void setHeaders(HttpHeaders headers) {
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.set("X-IG-API-KEY", igApiProperties.getKey());
    }

    private AuthenticationRequest createAuthenticationRequest() {
        return new AuthenticationRequest(igApiProperties.getUsername(),
                igApiProperties.getPassword());
    }

    private void handleTokens(String securityToken, String cst) {
        if (securityToken == null || securityToken.isEmpty()){
            throw new RuntimeException("Missing X-SECURITY-TOKEN in response");
        }
        if (cst == null || cst.isEmpty()){
            throw new RuntimeException("Missing CST in response");
        }
        log.info("Authentication successful, obtained security tokens");
        tokenStorageService.storeTokens(cst, securityToken);
    }
}

