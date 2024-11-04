package com.example.tripadvisor.dto;

import lombok.Builder;

@Builder
public record Accommodation(
        String name,
        Integer price,
        Double rating,
        String type
) {
}
