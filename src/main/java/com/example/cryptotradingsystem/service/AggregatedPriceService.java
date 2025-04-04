package com.example.cryptotradingsystem.service;

import com.example.cryptotradingsystem.entity.AggregatedPrice;

import java.util.List;

public interface AggregatedPriceService {
    List<AggregatedPrice> findLatestAggregatedPrices();
    void fetchAndAggregatePrices();
}
