package com.example.tripadvisor.client.TripPlanningServiceProviders;

import com.example.tripadvisor.dto.Transportation;
import com.example.tripadvisor.dto.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class WeatherServiceClient {
    private final RestClient client;

    public Weather getWeather(String airportCode) {
        return client
                .get()
                .uri(airportCode)
                .retrieve()
                .body(Weather.class);
    }
}
