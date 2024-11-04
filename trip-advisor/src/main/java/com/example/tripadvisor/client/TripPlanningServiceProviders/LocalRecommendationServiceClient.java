package com.example.tripadvisor.client.TripPlanningServiceProviders;

import com.example.tripadvisor.dto.Accommodation;
import com.example.tripadvisor.dto.Event;
import com.example.tripadvisor.dto.LocalRecommendations;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
public class LocalRecommendationServiceClient {
    private final RestClient client;

    public LocalRecommendations getLocalRecommendation(String airportCode) {
        return client
                .get()
                .uri(airportCode)
                .retrieve()
                .body(LocalRecommendations.class);
    }
}
