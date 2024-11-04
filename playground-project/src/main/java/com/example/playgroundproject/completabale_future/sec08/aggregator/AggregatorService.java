package com.example.playgroundproject.completabale_future.sec08.aggregator;

import com.example.playgroundproject.executor_service.sec07.externalService.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
public class AggregatorService {
    private final ExecutorService executorService;

    public ProductDTO getProduct(int id) {
        var productDescCompFuture = CompletableFuture
                .supplyAsync(() ->Client.getProduct(id),executorService)
                .exceptionally(ex->"product-not-found");

        var productRatingCompFuture = CompletableFuture
                .supplyAsync(() ->Client.getRating(id),executorService)
                .exceptionally(ex-> -1) //in case of exception return -1 as rating for product
                .orTimeout(1250, TimeUnit.MILLISECONDS)
                .exceptionally(ex-> {
                    log.info("Error: {}", ex.getMessage());
                    return null;
                });

        //since we handled exceptions, we can remove get() and replace it with join()
        return ProductDTO.builder()
                .id(id)
                .description(productDescCompFuture.join())
                .rating(productRatingCompFuture.join())
                .build();
    }
}
