package com.example.experttrader.service;

import com.example.experttrader.dto.alphavantage.EMAResponse;
import org.springframework.stereotype.Service;

@Service
public class StrategyService {

    private final AlphaVantageService alphaVantageService;

    public StrategyService(AlphaVantageService alphaVantageService) {
        this.alphaVantageService = alphaVantageService;
    }

    public void evaluateStrategy(String symbol){
        EMAResponse hourlyEMA100 = alphaVantageService.getEma(symbol,"60min",100);
        EMAResponse hourlyEMA10 = alphaVantageService.getEma(symbol,"60min",10);


    }
}
