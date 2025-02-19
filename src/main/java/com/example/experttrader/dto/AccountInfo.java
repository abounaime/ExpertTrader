package com.example.experttrader.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountInfo {
    @JsonProperty("balance")
    private double balance;

    @JsonProperty("deposit")
    private double deposit;

    @JsonProperty("profitLoss")
    private double profitLoss;

    @JsonProperty("available")
    private double available;
}
