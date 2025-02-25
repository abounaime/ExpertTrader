package com.example.experttrader.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "alpha.api")
@Data
public class AlphaVantageApiProperties {
    private String key;
    private String url;
}
