package com.example.experttrader.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

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


    private final RestTemplate restTemplate;
    public IgAuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public String authenticate(){
        String authUrl = baseUrl + "/session";
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-IG-API-KEY", apiKey);
        headers.add("Content-Type", "application/json");

        String body = String.format("{\"identifier\":\"%s\", \"password\":\"%s\"}",
                username, password);

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(authUrl, request, String.class);

        return response.getHeaders().getFirst("X-SECURITY-TOKEN");
    }

}
