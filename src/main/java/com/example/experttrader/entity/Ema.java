package com.example.experttrader.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ema")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    private String emaValue;
    private String timePeriod;
    private String interval;
    private LocalDateTime timeStamp;
}
