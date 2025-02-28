package com.example.experttrader.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ig.api")
public class IgApiProperties {
    private String baseurl;
    private String username;
    private String password;
    private String key;

    public String getBaseurl() {
        return baseurl;
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
