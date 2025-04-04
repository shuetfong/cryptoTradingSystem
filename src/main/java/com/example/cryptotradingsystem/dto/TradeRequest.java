package com.example.cryptotradingsystem.dto;

import com.example.cryptotradingsystem.enumeration.TradingPair;
import com.example.cryptotradingsystem.enumeration.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeRequest {
    @NotNull(message = "Invalid transaction type")
    private TransactionType transactionType;
    @NotNull(message = "Invalid trading pair")
    private TradingPair tradingPair;
    @Positive(message = "Quantity must be a positive number")
    private BigDecimal quantity;
}
