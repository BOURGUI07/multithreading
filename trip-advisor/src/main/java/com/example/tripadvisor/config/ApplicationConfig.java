package com.example.tripadvisor.config;

import com.example.tripadvisor.client.FlightSearchReservationProviders.FlightReservationServiceClient;
import com.example.tripadvisor.client.FlightSearchReservationProviders.FlightSearchServiceClient;
import com.example.tripadvisor.client.TripPlanningServiceProviders.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnThreading;
import org.springframework.boot.autoconfigure.thread.Threading;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ApplicationConfig {

    @Bean
    @ConditionalOnThreading(Threading.VIRTUAL) // if spring.threads.virtual.enabled=true then he will execute this
    public ExecutorService virtual(){
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Bean
    @ConditionalOnThreading(Threading.PLATFORM) // if spring.threads.virtual.enabled=false then he will execute this
    public ExecutorService platform(){
        return Executors.newFixedThreadPool(500);
    }

    private RestClient buildRestClient(String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public AccommodationServiceClient buildAccommodationServiceClient(@Value("${accommodation.url}") String baseUrl) {
        return new AccommodationServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public WeatherServiceClient buildWeatherServiceClient(@Value("${weather.url}") String baseUrl) {
        return new WeatherServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public EventServiceClient buildEventServiceClient(@Value("${event.url}") String baseUrl) {
        return new EventServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public TransportationServiceClient buildTransportationServiceClient(@Value("${transport.url}") String baseUrl) {
        return new TransportationServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public LocalRecommendationServiceClient buildLocalRecommendationServiceClient(@Value("${local.recommendation.url}") String baseUrl) {
        return new LocalRecommendationServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public FlightSearchServiceClient buildFlightSearchServiceClient(@Value("${flight.search.url}") String baseUrl) {
        return new FlightSearchServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public FlightReservationServiceClient buildFlightReservationServiceClient(@Value("${flight.reservation.url}") String baseUrl) {
        return new FlightReservationServiceClient(buildRestClient(baseUrl));
    }
}
