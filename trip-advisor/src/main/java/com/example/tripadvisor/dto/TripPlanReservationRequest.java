package com.example.tripadvisor.dto;

import java.time.LocalDate;

public record TripPlanReservationRequest(
        String departure,
        String arrival,
        LocalDate date
) {
}
