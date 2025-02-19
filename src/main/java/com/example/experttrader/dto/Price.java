package com.example.experttrader.dto;

import lombok.Data;

@Data
public class Price {
    private String snapshotTime;
    private String snapshotTimeUTC;
    private PriceDetail openPrice;
    private PriceDetail closePrice;
    private PriceDetail highPrice;
    private PriceDetail lowPrice;
    private int lastTradedVolume;
}
