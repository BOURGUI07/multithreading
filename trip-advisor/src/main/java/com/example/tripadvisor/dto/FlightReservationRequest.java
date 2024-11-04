package com.example.tripadvisor.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record FlightReservationRequest(
        String departure,
        String arrival,
        LocalDate tripDate,
        String flightNumber
) {
}
