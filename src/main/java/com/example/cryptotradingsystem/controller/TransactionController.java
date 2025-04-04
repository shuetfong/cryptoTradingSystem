package com.example.cryptotradingsystem.controller;

import com.example.cryptotradingsystem.dto.ResponseResult;
import com.example.cryptotradingsystem.dto.TradeRequest;
import com.example.cryptotradingsystem.entity.Transaction;
import com.example.cryptotradingsystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/trans")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseResult<?> submitTransaction(@Validated @RequestBody TradeRequest trade) {
        Transaction transaction = transactionService.submitTransaction(trade);
        return ResponseResult.success(transaction);
    }

    @GetMapping
    public ResponseResult<?> getTransactions() {
        List<Transaction> transactions = transactionService.findTransactions();
        return ResponseResult.success(transactions);
    }
}
