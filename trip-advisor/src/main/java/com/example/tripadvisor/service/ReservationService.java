package com.example.tripadvisor.service;

import com.example.tripadvisor.client.FlightSearchReservationProviders.FlightReservationServiceClient;
import com.example.tripadvisor.client.FlightSearchReservationProviders.FlightSearchServiceClient;
import com.example.tripadvisor.dto.FlightReservationRequest;
import com.example.tripadvisor.dto.FlightReservationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final FlightReservationServiceClient flightReservationServiceClient;
    private final FlightSearchServiceClient flightSearchServiceClient;


    public FlightReservationResponse reserve(FlightReservationRequest request) {
        var flights = flightSearchServiceClient.getFlights(request.departure(), request.arrival());
        var bestDeal = flights.stream().min((a,b) ->a.price()-b.price());
        var flight = bestDeal.orElseThrow(() ->new IllegalStateException("no flights found"));
        var flightRequest = FlightReservationRequest.builder()
                .departure(request.departure())
                .arrival(request.arrival())
                .flightNumber(flight.flightNumber())
                .tripDate(request.tripDate())
                .build();
        return flightReservationServiceClient.reserveFlight(flightRequest);

    }


}
