package com.example.tripadvisor.client.TripPlanningServiceProviders;

import com.example.tripadvisor.dto.LocalRecommendations;
import com.example.tripadvisor.dto.Transportation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class TransportationServiceClient {
    private final RestClient client;

    public Transportation getTransportation(String airportCode) {
        return client
                .get()
                .uri(airportCode)
                .retrieve()
                .body(Transportation.class);
    }
}
