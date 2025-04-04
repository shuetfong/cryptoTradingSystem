package com.example.cryptotradingsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletId implements Serializable {
    @Serial
    private static final long serialVersionUID = 6228688824270122815L;

    private String userId;
    private String currency;
}
