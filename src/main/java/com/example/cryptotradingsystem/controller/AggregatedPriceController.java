package com.example.cryptotradingsystem.controller;

import com.example.cryptotradingsystem.dto.ResponseResult;
import com.example.cryptotradingsystem.entity.AggregatedPrice;
import com.example.cryptotradingsystem.service.AggregatedPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/agg-price")
public class AggregatedPriceController {

    private final AggregatedPriceService aggregatedPriceService;

    @GetMapping("/latest")
    public ResponseResult<?> getLatestPrices() {
        List<AggregatedPrice> prices = aggregatedPriceService.findLatestAggregatedPrices();
        return ResponseResult.success(prices);
    }
}
