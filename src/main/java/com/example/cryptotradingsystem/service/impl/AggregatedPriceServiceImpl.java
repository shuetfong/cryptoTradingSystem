package com.example.cryptotradingsystem.service.impl;

import com.example.cryptotradingsystem.dto.BinanceTicker;
import com.example.cryptotradingsystem.dto.HuobiTicker;
import com.example.cryptotradingsystem.entity.AggregatedPrice;
import com.example.cryptotradingsystem.enumeration.TradingPair;
import com.example.cryptotradingsystem.repository.AggregatedPriceRepository;
import com.example.cryptotradingsystem.service.AggregatedPriceService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AggregatedPriceServiceImpl implements AggregatedPriceService {

    private final AggregatedPriceRepository aggregatedPriceRepository;
    private final RestTemplate restTemplate;

    private static final Set<String> TRADING_PAIRS = Arrays.stream(TradingPair.values()).map(TradingPair::toString).collect(Collectors.toSet());

    @Override
    @Transactional(readOnly = true)
    public List<AggregatedPrice> findLatestAggregatedPrices() {
        return aggregatedPriceRepository.findLatestAggregatedPrices();
    }

    @Override
    @Scheduled(fixedRate = 10000)
    @Transactional
    public void fetchAndAggregatePrices() {
        List<BinanceTicker> binanceTickers = new ArrayList<>();
        try {
            binanceTickers.addAll(fetchBinancePrices());
        } catch (Exception e) {
            log.error("Failed to fetch Binance prices: {}", e.getMessage());
        }

        List<HuobiTicker> huobiTickers = new ArrayList<>();
        try {
            huobiTickers.addAll(fetchHuobiPrices());
        } catch (Exception e) {
            log.error("Failed to fetch Huobi prices: {}", e.getMessage());
        }

//        log.info("binanceTickers {}", binanceTickers);
//        log.info("huobiTickers {}", huobiTickers);

        aggregateAndSave(binanceTickers, huobiTickers);
    }

    private List<BinanceTicker> fetchBinancePrices() {
        String url = "https://api.binance.com/api/v3/ticker/bookTicker";
        // response: [{}, {}, {}]
        BinanceTicker[] binanceTickers = restTemplate.getForObject(url, BinanceTicker[].class);

        return Arrays.stream(binanceTickers)
                .filter(ticker -> TRADING_PAIRS.contains(ticker.getSymbol()))
                .collect(Collectors.toList());
    }

    private List<HuobiTicker> fetchHuobiPrices() {
        String url = "https://api.huobi.pro/market/tickers";
        // response: { data: [{}, {}, {}] }
        String huobiResponse = restTemplate.getForObject(url, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(huobiResponse);
            JsonNode dataNode = rootNode.get("data");

            HuobiTicker[] huobiTickers = objectMapper.treeToValue(dataNode, HuobiTicker[].class);

            return Arrays.stream(huobiTickers)
                    .filter(ticker -> TRADING_PAIRS.contains(ticker.getSymbol().toUpperCase()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    private void aggregateAndSave(List<BinanceTicker> binanceTickers, List<HuobiTicker> huobiTickers) {
        LocalDateTime now = LocalDateTime.now();
        TRADING_PAIRS.forEach(pair -> {
            List<BigDecimal> bidPrices = new ArrayList<>();
            List<BigDecimal> askPrices = new ArrayList<>();

            binanceTickers.stream()
                    .filter(t -> t.getSymbol().equals(pair))
                    .forEach(t -> {
                        bidPrices.add(t.getBidPrice());
                        askPrices.add(t.getAskPrice());
                    });

            huobiTickers.stream()
                    .filter(t -> t.getSymbol().equalsIgnoreCase(pair))
                    .forEach(t -> {
                        bidPrices.add(t.getBid());
                        askPrices.add(t.getAsk());
                    });

            if(bidPrices.isEmpty() || askPrices.isEmpty()) {
                log.warn("No prices found for pair: {}", pair);
                return;
            }

            BigDecimal bestBid = Collections.max(bidPrices);
            BigDecimal bestAsk = Collections.min(askPrices);

            AggregatedPrice aggregatedPrice = new AggregatedPrice();
            aggregatedPrice.setTradingPair(TradingPair.valueOf(pair));
            aggregatedPrice.setBidPrice(bestBid);
            aggregatedPrice.setAskPrice(bestAsk);
            aggregatedPrice.setCreatedDate(now);
            aggregatedPriceRepository.save(aggregatedPrice);
        });
    }
}
