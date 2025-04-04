package com.example.cryptotradingsystem.service;

import com.example.cryptotradingsystem.entity.Wallet;

import java.util.List;

public interface WalletService {
    List<Wallet> findUserWallets();
}
