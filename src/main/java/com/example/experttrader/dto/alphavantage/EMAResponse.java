package com.example.experttrader.dto.alphavantage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class EMAResponse {
    @JsonProperty("Meta Data")
    private MetaData metaData;

    @JsonProperty("Technical Analysis: EMA")
    private Map<String, EMAData> technicalAnalysisEMA;
}
