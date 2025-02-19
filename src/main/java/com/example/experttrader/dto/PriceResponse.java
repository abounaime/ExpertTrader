package com.example.experttrader.dto;

import lombok.Data;

import java.util.List;

@Data
public class PriceResponse {
    private List<Price> prices;
    private String instrumentType;
    private Metadata metadata;
}
