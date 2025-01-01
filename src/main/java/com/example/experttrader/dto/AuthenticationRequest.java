package com.example.experttrader.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private final String identifier;
    private final String password;
    public AuthenticationRequest(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }
}
