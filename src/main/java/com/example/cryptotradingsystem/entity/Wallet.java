package com.example.cryptotradingsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(WalletId.class)
@Table(name = "wallet")
public class Wallet {
    @Id
    private String userId = "1";

    @Id
    private String currency;

    private BigDecimal availableBalance;
}
