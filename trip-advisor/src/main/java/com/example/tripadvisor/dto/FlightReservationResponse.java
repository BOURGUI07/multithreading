package com.example.tripadvisor.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record FlightReservationResponse(
        String departure,
        String arrival,
        LocalDate tripDate,
        String flightNumber,
        String reservationId,
        Integer price
) {
}
