package com.example.cryptotradingsystem.dto;

import com.example.cryptotradingsystem.enumeration.TradingPair;
import com.example.cryptotradingsystem.enumeration.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeRequest {
    @NotNull
    private TransactionType transactionType;
    @NotNull
    private TradingPair tradingPair;
    @NotNull
    @Positive
    private BigDecimal quantity;
}
