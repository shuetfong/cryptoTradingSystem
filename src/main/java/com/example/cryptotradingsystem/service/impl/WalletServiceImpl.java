package com.example.cryptotradingsystem.service.impl;

import com.example.cryptotradingsystem.entity.Wallet;
import com.example.cryptotradingsystem.repository.WalletRepository;
import com.example.cryptotradingsystem.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WalletServiceImpl implements WalletService  {

    private final WalletRepository walletRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Wallet> findUserWallets() {
        return walletRepository.findByUserId("1");
    }
}
