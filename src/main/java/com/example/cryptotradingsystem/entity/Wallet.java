package com.example.cryptotradingsystem.entity;

import jakarta.persistence.*;
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

    @Column(precision = 20, scale = 8)
    private BigDecimal availableBalance;
}
