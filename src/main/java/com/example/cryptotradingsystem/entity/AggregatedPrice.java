package com.example.cryptotradingsystem.entity;

import com.example.cryptotradingsystem.enumeration.TradingPair;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aggregated_price")
public class AggregatedPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priceId;

    @Enumerated(EnumType.STRING)
    private TradingPair tradingPair;

    // SELL order
    @Column(precision = 20, scale = 8)
    private BigDecimal bidPrice;

    // BUY order
    @Column(precision = 20, scale = 8)
    private BigDecimal askPrice;

    private LocalDateTime createdDate;
}
