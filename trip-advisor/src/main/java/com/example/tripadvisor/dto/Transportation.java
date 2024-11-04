package com.example.tripadvisor.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record Transportation(
        List<CarRental> carRentals,
        List<PublicTransportation> publicTransportations
) {
}
