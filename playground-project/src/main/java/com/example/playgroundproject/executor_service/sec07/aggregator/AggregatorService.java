package com.example.playgroundproject.executor_service.sec07.aggregator;

import com.example.playgroundproject.executor_service.sec07.externalService.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientCodecCustomizer;

import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
@Slf4j
public class AggregatorService {
    private final ExecutorService executorService;

    public ProductDTO getProduct(int id) throws Exception {
        var productDescFuture = executorService.submit(() -> Client.getProduct(id));
        var productRatingFuture = executorService.submit(() -> Client.getRating(id));
        return ProductDTO.builder()
                .id(id)
                .description(productDescFuture.get())
                .rating(productRatingFuture.get())
                .build();
    }
}
