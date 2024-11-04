package com.example.playgroundproject.completabale_future.sec08.aggregator;

import lombok.Builder;

@Builder
public record ProductDTO(
        int id,
        String description,
        Integer rating
) {
}
