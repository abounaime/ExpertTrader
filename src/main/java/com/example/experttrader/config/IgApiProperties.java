package com.example.experttrader.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ig.api")
@Data
public class IgApiProperties {
    private String baseUrl;
    private String username;
    private String password;
    private String key;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getKey() {
        return key;
    }
}
