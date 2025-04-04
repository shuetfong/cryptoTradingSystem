package com.example.cryptotradingsystem.repository;

import com.example.cryptotradingsystem.entity.AggregatedPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AggregatedPriceRepository extends JpaRepository<AggregatedPrice, Long> {
    @Query("SELECT a FROM AggregatedPrice a WHERE a.createdDate = (SELECT MAX(b.createdDate) FROM AggregatedPrice b WHERE b.tradingPair = a.tradingPair)")
    List<AggregatedPrice> findLatestAggregatedPrices();
}
