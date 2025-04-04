package com.example.cryptotradingsystem.repository;

import com.example.cryptotradingsystem.entity.Wallet;
import com.example.cryptotradingsystem.entity.WalletId;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, WalletId> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Wallet> findByUserId(String userId);
}
