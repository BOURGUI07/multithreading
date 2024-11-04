package com.example.tripadvisor.client.TripPlanningServiceProviders;

import com.example.tripadvisor.dto.Accommodation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.awt.*;
import java.util.List;

@RequiredArgsConstructor
public class AccommodationServiceClient {
    private final RestClient client;

    public java.util.List<Accommodation> getAccommodations(String airportCode) {
        return client
                .get()
                .uri(airportCode)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Accommodation>>() {});
    }
}
