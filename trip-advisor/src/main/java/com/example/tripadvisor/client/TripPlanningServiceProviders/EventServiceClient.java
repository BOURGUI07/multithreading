package com.example.tripadvisor.client.TripPlanningServiceProviders;

import com.example.tripadvisor.dto.Accommodation;
import com.example.tripadvisor.dto.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
public class EventServiceClient {
    private final RestClient client;

    public java.util.List<Event> getEvents(String airportCode) {
        return client
                .get()
                .uri(airportCode)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Event>>() {});
    }
}
