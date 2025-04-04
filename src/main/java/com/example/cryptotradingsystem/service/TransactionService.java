package com.example.cryptotradingsystem.service;

import com.example.cryptotradingsystem.dto.TradeRequest;
import com.example.cryptotradingsystem.entity.Transaction;

public interface TransactionService {
    Transaction submitTransaction(TradeRequest trade);
}
