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
    public PricesService(IgApiProperties igApiProperties, WebClient webClient) {
        this.igApiProperties = igApiProperties;
        this.webClient = webClient;
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
        headers.set("X-IG-API-KEY", igApiProperties.getKey());
        headers.set("Version", "3");
    }
}
