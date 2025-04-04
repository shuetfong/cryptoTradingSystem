package com.example.cryptotradingsystem.service;

import com.example.cryptotradingsystem.dto.TradeRequest;
import com.example.cryptotradingsystem.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction submitTransaction(TradeRequest trade);
    List<Transaction> findTransactions();
}
