package com.example.experttrader.dto.alphavantage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EMAData {
    @JsonProperty("EMA")
    private String ema;
}
