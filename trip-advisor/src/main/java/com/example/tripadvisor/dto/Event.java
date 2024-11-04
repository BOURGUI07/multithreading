package com.example.tripadvisor.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record Event(
        LocalDate date,
        String name,
        String description
) {
}
