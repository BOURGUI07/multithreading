package com.example.tripadvisor.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record TripPlan(String airportCode,
                       List<Accommodation> accommodations,
                       Weather weather,
                       List<Event> events,
                       LocalRecommendations localRecommendations,
                       Transportation transportation) {
}
