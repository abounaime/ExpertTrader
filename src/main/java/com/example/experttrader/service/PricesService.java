package com.example.experttrader.service;

import com.example.experttrader.config.IgApiProperties;
import com.example.experttrader.dto.PriceResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Service
public class PricesService {

    private final WebClient webClient;
    private final IgApiProperties igApiProperties;
    private final TokenStorageService tokenStorageService;
    public PricesService(IgApiProperties igApiProperties,
                         WebClient.Builder webClientBuilder, TokenStorageService tokenStorageService) {
        this.igApiProperties = igApiProperties;
        this.tokenStorageService = tokenStorageService;
        this.webClient = webClientBuilder
                .baseUrl(igApiProperties.getBaseUrl())
                .build();
    }


    public Mono<PriceResponse> getHistoricalPrices(String epic) {

        return webClient.get()
                .uri("/prices/"+epic)
                .headers(this::setHeaders)
                .retrieve()
                .bodyToMono(PriceResponse.class);
    }

    private void setHeaders(HttpHeaders headers) {
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.set(igApiProperties.getKeyLabel(), igApiProperties.getKey());
        headers.set("Version", "3");
        headers.set(igApiProperties.getSecurityTokenLabel(), tokenStorageService.getSecurityTokenKey());
        headers.set(igApiProperties.getCstLabel(),tokenStorageService.getCstKey());

    }
}
