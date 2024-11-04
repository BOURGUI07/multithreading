package com.example.tripadvisor.dto;

import lombok.Builder;

@Builder
public record Weather(
        String conditions,
        Integer temperature
) {
}
