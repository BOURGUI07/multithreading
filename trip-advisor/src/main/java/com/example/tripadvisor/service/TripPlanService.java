package com.example.tripadvisor.service;

import com.example.tripadvisor.client.TripPlanningServiceProviders.*;
import com.example.tripadvisor.dto.TripPlan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
@Slf4j
public class TripPlanService {
    private final AccommodationServiceClient accommodationServiceClient;
    private final TransportationServiceClient transportationServiceClient;
    private final EventServiceClient eventServiceClient;
    private final WeatherServiceClient weatherServiceClient;
    private final LocalRecommendationServiceClient localRecommendationServiceClient;

    private final ExecutorService executor;


    //make 5 parallel calls
    public TripPlan getTripPlan(String airportCode){
        var events = executor.submit(() -> eventServiceClient.getEvents(airportCode));
        var weather = executor.submit(() -> weatherServiceClient.getWeather(airportCode));
        var transportation = executor.submit(() -> transportationServiceClient.getTransportation(airportCode));
        var accommodation = executor.submit(() -> accommodationServiceClient.getAccommodations(airportCode));
        var localRecommendation = executor.submit(() -> localRecommendationServiceClient.getLocalRecommendation(airportCode));
        return TripPlan.builder()
                .airportCode(airportCode)
                .localRecommendations(getOrElse(localRecommendation,null))
                .transportation(getOrElse(transportation,null))
                .weather(getOrElse(weather,null))
                .events(getOrElse(events, Collections.emptyList()))
                .accommodations(getOrElse(accommodation,Collections.emptyList()))
                .build();
    }

    private <T> T getOrElse(Future<T> future, T defaultValue) {
        try {
            return future.get();
        } catch (Exception e) {
            log.error( "Error: {}",e.getMessage());
        }
        return defaultValue;
    }


}
