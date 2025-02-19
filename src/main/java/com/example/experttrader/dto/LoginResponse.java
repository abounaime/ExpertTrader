package com.example.experttrader.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {
    private String accountType;

    @JsonProperty("accountInfo")
    private AccountInfo accountInfo;

    @JsonProperty("currencyIsoCode")
    private String currencyIsoCode;

    @JsonProperty("currencySymbol")
    private String currencySymbol;

    @JsonProperty("currentAccountId")
    private String currentAccountId;

    @JsonProperty("lightstreamerEndpoint")
    private String lightstreamerEndpoint;

    @JsonProperty("accounts")
    private List<Account> accounts;

    @JsonProperty("clientId")
    private String clientId;

    @JsonProperty("timezoneOffset")
    private int timezoneOffset;

    @JsonProperty("hasActiveDemoAccounts")
    private boolean hasActiveDemoAccounts;

    @JsonProperty("hasActiveLiveAccounts")
    private boolean hasActiveLiveAccounts;

    @JsonProperty("trailingStopsEnabled")
    private boolean trailingStopsEnabled;

    @JsonProperty("reroutingEnvironment")
    private String reroutingEnvironment;

    @JsonProperty("dealingEnabled")
    private boolean dealingEnabled;
}
