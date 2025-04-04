package com.example.cryptotradingsystem.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BinanceTicker {
    private String symbol;
    private BigDecimal bidPrice;
    private BigDecimal askPrice;
}
