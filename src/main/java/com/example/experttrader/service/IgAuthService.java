package com.example.experttrader.service;

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
    private final String clientId = "";
    private final String clientSecret = "";
    private final String tokenUrl = "";

    private final RestTemplate restTemplate;

    public IgAuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public String authenticate(){
        //Prepare the credentials for basic auth
        String authString = clientId + ":" + clientSecret;
        String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());

        //Create HttpHeaders with Basic Authentication
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Basic " + encodedAuthString);
        httpHeaders.set("Content-Type", "application/x-www-form-urlencoded");

        //Create the request body for token exchange
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        //Send the request to obtain access token
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(tokenUrl,
                HttpMethod.POST, entity, String.class);

        // Parse the access token from the response
        String responseBody = response.getBody();

        // You'll want to extract the access token here
        // Assuming the response is a JSON object containing the token
        String accessToken = responseBody;

        return accessToken;
    }
}
