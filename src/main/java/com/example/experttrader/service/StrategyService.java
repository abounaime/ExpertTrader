package com.example.experttrader.service;

import com.example.experttrader.dto.alphavantage.EMAResponse;
import org.springframework.stereotype.Service;

@Service
public class StrategyService {

    private final EmaService emaService;

    public StrategyService(EmaService emaService) {
        this.emaService = emaService;
    }

    public void evaluateStrategy(String symbol){
//        EMAResponse hourlyEMA100 = emaService.getEma(symbol,"60min","100");
//        EMAResponse hourlyEMA10 = emaService.getEma(symbol,"60min","10");

    }
}
