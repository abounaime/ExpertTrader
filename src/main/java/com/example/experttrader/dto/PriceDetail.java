package com.example.experttrader.dto;

import lombok.Data;

@Data
public class PriceDetail {
    private Double bid;
    private Double ask;
    private Double lastTraded;
}
