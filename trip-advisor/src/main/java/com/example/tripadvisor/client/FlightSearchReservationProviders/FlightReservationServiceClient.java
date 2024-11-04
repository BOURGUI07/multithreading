package com.example.tripadvisor.client.FlightSearchReservationProviders;

import com.example.tripadvisor.dto.FlightReservationRequest;
import com.example.tripadvisor.dto.FlightReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class FlightReservationServiceClient {
    private final RestClient restClient;

    public FlightReservationResponse reserveFlight(FlightReservationRequest flightReservationRequest) {
        return restClient
                .post()
                .body(flightReservationRequest)
                .retrieve()
                .body(FlightReservationResponse.class);
    }
}
