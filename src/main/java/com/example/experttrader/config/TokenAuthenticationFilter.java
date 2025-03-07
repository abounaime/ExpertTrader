package com.example.experttrader.config;

import com.example.experttrader.service.IgAuthService;
import com.example.experttrader.service.TokenStorageService;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

public class TokenAuthenticationFilter {
    private final TokenStorageService tokenStorageService;

    public TokenAuthenticationFilter(TokenStorageService tokenStorageService) {
        this.tokenStorageService = tokenStorageService;
    }

    public ExchangeFilterFunction filter() {
        return (request, next) -> addAuthHeader(request)
                .flatMap(next::exchange);
    }

    private Mono<ClientRequest> addAuthHeader(ClientRequest request){
        Mono<String> securityToken = Mono.just(tokenStorageService.getSecurityTokenKey());
        Mono<String> cst = Mono.just(tokenStorageService.getCstKey());

        return Mono.zip(securityToken, cst)
                .map(tuple -> ClientRequest.from(request)
                        .header("X-SECURITY-TOKEN", tuple.getT1())
                        .header("CST", tuple.getT2())
                        .build());
    }
}
