package com.example.experttrader.service;

import com.example.experttrader.config.AlphaVantageApiProperties;
import com.example.experttrader.dto.alphavantage.EMAResponse;
import com.example.experttrader.entity.Ema;
import com.example.experttrader.repository.EmaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmaService {
    private final EmaRepository emaRepository;
    private final AlphaVantageApiProperties alphaVantageProperties;
    private final RestTemplate restTemplate;


    public EmaService(EmaRepository emaRepository, AlphaVantageApiProperties alphaVantageProperties,
                               RestTemplate restTemplate) {
        this.emaRepository = emaRepository;
        this.alphaVantageProperties = alphaVantageProperties;
        this.restTemplate = restTemplate;
    }


    public Optional<String> getEma(String symbol, String interval, String timePeriod) {
        return emaRepository.findTop1BySymbolAndTimePeriodAndIntervalOrderByTimeStampDesc(symbol, timePeriod, interval)
                .map(Ema::getEmaValue)
                .or(() -> {
                    EMAResponse response = fetchEmaFromAlphaVantage(symbol, interval, timePeriod);
                    Optional<String> fetchedEma = Optional.ofNullable(response)
                            .map(EMAResponse::getTechnicalAnalysisEMA)
                            .flatMap(emap -> emap.entrySet().stream().findFirst())
                            .map(entry -> entry.getValue().getEma());

                    fetchedEma.ifPresent(emaValue -> {
                        Ema ema = new Ema(null, symbol, timePeriod, interval, emaValue, LocalDateTime.now());
                        emaRepository.save(ema);
                    });
                    return fetchedEma;
                });
    }


    public EMAResponse fetchEmaFromAlphaVantage (String symbol, String interval, String timePeriod) {
        String url = UriComponentsBuilder.fromUriString(alphaVantageProperties.getUrl())
                .queryParam("function", "EMA")
                .queryParam("symbol", symbol)
                .queryParam("interval", interval)
                .queryParam("time_period", timePeriod)
                .queryParam("series_type", "close")
                .queryParam("apikey", alphaVantageProperties.getKey())
                .toUriString();

        return restTemplate.getForObject(url, EMAResponse.class);
    }

}
