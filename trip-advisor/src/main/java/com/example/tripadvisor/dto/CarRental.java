package com.example.tripadvisor.dto;

import lombok.Builder;

@Builder
public record CarRental(
        String agency,
        Integer price
) {
}
