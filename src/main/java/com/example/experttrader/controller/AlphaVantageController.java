package com.example.experttrader.controller;

import com.example.experttrader.dto.alphavantage.EMAResponse;
import com.example.experttrader.service.AlphaVantageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping()
public class AlphaVantageController {

    private final AlphaVantageService alphaVantageService;

    public AlphaVantageController(AlphaVantageService alphaVantageService) {
        this.alphaVantageService = alphaVantageService;
    }

    @GetMapping("/ema/{symbol}/{interval}/{timePeriod}")
    public ResponseEntity<Optional<EMAResponse>> getHourlyEma(@PathVariable String symbol, @PathVariable String interval,
                                                              @PathVariable int timePeriod) {
        EMAResponse response = alphaVantageService.getEma(symbol, interval, timePeriod);
        return ResponseEntity.ok(Optional.of(response));
    }
}
