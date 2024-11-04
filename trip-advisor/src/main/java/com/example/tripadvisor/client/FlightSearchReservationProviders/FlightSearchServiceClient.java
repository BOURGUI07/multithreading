package com.example.tripadvisor.client.FlightSearchReservationProviders;

import com.example.tripadvisor.dto.Flight;
import com.example.tripadvisor.dto.FlightReservationRequest;
import com.example.tripadvisor.dto.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
public class FlightSearchServiceClient {
    private final RestClient client;

    public List<Flight> getFlights(String departure, String arrival) {
        return client
                .get()
                .uri("{departure}/{arrival}",departure,arrival)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Flight>>() {});
    }
}
