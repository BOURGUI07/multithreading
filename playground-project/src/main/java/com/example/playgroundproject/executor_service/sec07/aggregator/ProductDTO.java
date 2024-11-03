package com.example.playgroundproject.executor_service.sec07.aggregator;

import lombok.Builder;

@Builder
public record ProductDTO(
        int id,
        String description,
        Integer rating
) {
}
