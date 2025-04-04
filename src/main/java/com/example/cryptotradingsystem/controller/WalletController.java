package com.example.cryptotradingsystem.controller;

import com.example.cryptotradingsystem.dto.ResponseResult;
import com.example.cryptotradingsystem.entity.Wallet;
import com.example.cryptotradingsystem.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wallet")
public class WalletController {
    private final WalletService walletService;

    @GetMapping
    public ResponseResult<?> getWallet() {
        List<Wallet> wallets = walletService.findUserWallets();
        return ResponseResult.success(wallets);
    }
}
