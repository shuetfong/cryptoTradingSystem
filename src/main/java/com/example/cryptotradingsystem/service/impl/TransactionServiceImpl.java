package com.example.cryptotradingsystem.service.impl;

import com.example.cryptotradingsystem.dto.TradeRequest;
import com.example.cryptotradingsystem.entity.AggregatedPrice;
import com.example.cryptotradingsystem.entity.Transaction;
import com.example.cryptotradingsystem.entity.Wallet;
import com.example.cryptotradingsystem.enumeration.TransactionType;
import com.example.cryptotradingsystem.repository.AggregatedPriceRepository;
import com.example.cryptotradingsystem.repository.TransactionRepository;
import com.example.cryptotradingsystem.repository.WalletRepository;
import com.example.cryptotradingsystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final AggregatedPriceRepository aggregatedPriceRepository;
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    private Wallet createNewCryptoWallet(String crypto) {
        Wallet wallet = new Wallet();
        wallet.setCurrency(crypto);
        wallet.setAvailableBalance(BigDecimal.ZERO);
        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public Transaction submitTransaction(TradeRequest trade) {
        // get latest prices
        List<AggregatedPrice> latestAggregatedPrices = aggregatedPriceRepository.findLatestAggregatedPrices();
        AggregatedPrice aggregatedPrice = latestAggregatedPrices.stream()
                .filter(p -> p.getTradingPair().equals(trade.getTradingPair()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No prices found for pair: " + trade.getTradingPair()));

        boolean isBuy = trade.getTransactionType().equals(TransactionType.BUY);
        BigDecimal quantity = trade.getQuantity().setScale(8, RoundingMode.HALF_UP);
        BigDecimal price = isBuy ? aggregatedPrice.getAskPrice() : aggregatedPrice.getBidPrice();
        BigDecimal totalAmount = quantity.multiply(price).setScale(8, RoundingMode.HALF_UP);

        String crypto = trade.getTradingPair().toString().replace("USDT", "");
        List<Wallet> wallets = walletRepository.findByUserId("1");
        Wallet walletUSDT = null;
        Wallet walletCrypto = null;
        for (Wallet wallet : wallets) {
            if ("USDT".equals(wallet.getCurrency())) {
                walletUSDT = wallet;
            }
            if (crypto.equals(wallet.getCurrency())) {
                walletCrypto = wallet;
            }
        }
        if (walletUSDT == null) {
            throw new RuntimeException("USDT wallet not found");
        }
        if (walletCrypto == null) {
            walletCrypto = createNewCryptoWallet(crypto);
        }

        if (isBuy) {
            if (walletUSDT.getAvailableBalance().compareTo(totalAmount) < 0) {
                throw new RuntimeException("Insufficient USDT balance");
            }
            walletUSDT.setAvailableBalance(walletUSDT.getAvailableBalance().subtract(totalAmount));
            walletCrypto.setAvailableBalance(walletCrypto.getAvailableBalance().add(quantity));
        } else {
            if (walletCrypto.getAvailableBalance().compareTo(quantity) < 0) {
                throw new RuntimeException("Insufficient " + crypto + " balance");
            }
            walletCrypto.setAvailableBalance(walletCrypto.getAvailableBalance().subtract(quantity));
            walletUSDT.setAvailableBalance(walletUSDT.getAvailableBalance().add(totalAmount));
        }

        walletRepository.saveAll(List.of(walletUSDT, walletUSDT));

        Transaction transaction = new Transaction();
        transaction.setTradingPair(trade.getTradingPair());
        transaction.setTransactionType(trade.getTransactionType());
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setTotalAmount(totalAmount);
        transactionRepository.save(transaction);

        return transaction;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findTransactions() {
        return transactionRepository.findByUserIdOrderByCreatedDateDesc("1");
    }
}
