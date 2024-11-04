package com.example.tripadvisor.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record Flight(
        LocalDate date,
        String flightNumber,
        Integer price,
        String airline,
        Integer flightDurationInMinutes
) {
}
