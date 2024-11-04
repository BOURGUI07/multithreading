package com.example.tripadvisor.dto;

import lombok.Builder;

@Builder
public record PublicTransportation(
        String type,
        Double price
) {
}
