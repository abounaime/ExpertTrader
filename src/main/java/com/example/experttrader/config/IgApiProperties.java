package com.example.experttrader.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
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
}
