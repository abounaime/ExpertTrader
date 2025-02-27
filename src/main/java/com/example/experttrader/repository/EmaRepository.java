package com.example.experttrader.repository;

import com.example.experttrader.entity.Ema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmaRepository extends JpaRepository<Ema, Long> {
    Optional<Ema> findTop1BySymbolAndTimePeriodAndIntervalOrderByTimeStampDesc(String symbol, String timePeriod, String interval);
}
