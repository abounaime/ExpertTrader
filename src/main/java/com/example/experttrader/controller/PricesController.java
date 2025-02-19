package com.example.experttrader.controller;

import com.example.experttrader.dto.PriceResponse;
import com.example.experttrader.service.PricesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/prices")
public class PricesController {
    private final PricesService pricesService;

    public PricesController(PricesService pricesService) {
        this.pricesService = pricesService;
    }

    @GetMapping("/{epic}")
    public Mono<PriceResponse> getHistoricalPrices(@PathVariable String epic){
        return pricesService.getHistoricalPrices(epic);
    }
}
