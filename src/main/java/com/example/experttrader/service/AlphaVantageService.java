package com.example.experttrader.service;

import com.example.experttrader.config.AlphaVantageApiProperties;
import com.example.experttrader.config.AlphaVantageApiProperties;
import com.example.experttrader.dto.alphavantage.EMAResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AlphaVantageService {
    private final AlphaVantageApiProperties alphaVantageProperties;
    private final RestTemplate restTemplate;
    public AlphaVantageService(AlphaVantageApiProperties alphaVantageProperties, RestTemplate restTemplate) {
        this.alphaVantageProperties = alphaVantageProperties;
        this.restTemplate = restTemplate;
    }

    public EMAResponse getEma (String symbol, String interval, int timePeriod) {
        String url = UriComponentsBuilder.fromUriString(alphaVantageProperties.getUrl())
                .queryParam("function", "EMA")
                .queryParam("symbol", symbol)
                .queryParam("interval", interval)
                .queryParam("time_period", timePeriod)
                .queryParam("series_type", "close")
                .queryParam("apikey", alphaVantageProperties.getKey())
                .toUriString();

        return restTemplate.getForObject(url, EMAResponse.class);
    }


}
