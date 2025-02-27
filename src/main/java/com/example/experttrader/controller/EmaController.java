package com.example.experttrader.controller;

import com.example.experttrader.service.EmaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class EmaController {

    private final EmaService emaService;

    public EmaController(EmaService emaService) {
        this.emaService = emaService;
    }


    @GetMapping("/ema/{symbol}/{interval}/{timePeriod}")
    public ResponseEntity<String> getHourlyEma(@PathVariable String symbol, @PathVariable String interval,
                                                              @PathVariable String timePeriod) {
        return emaService.getEma(symbol, interval, timePeriod)
                .map(ResponseEntity::ok) // If present, return 200 OK with the EMA value
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
