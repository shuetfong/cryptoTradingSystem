package com.example.cryptotradingsystem.config;

import com.example.cryptotradingsystem.entity.Wallet;
import com.example.cryptotradingsystem.entity.WalletId;
import com.example.cryptotradingsystem.repository.WalletRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DatabaseInitializer {
    private final WalletRepository walletRepository;

    public DatabaseInitializer(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @PostConstruct
    public void initialize() {
        if (!walletRepository.existsById(new WalletId("1", "USDT"))) {
            Wallet usdtWallet = new Wallet();
            usdtWallet.setCurrency("USDT");
            usdtWallet.setAvailableBalance(new BigDecimal("50000.00000000"));
            walletRepository.save(usdtWallet);
        }
    }
}
