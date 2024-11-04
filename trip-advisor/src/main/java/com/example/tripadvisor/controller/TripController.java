package com.example.tripadvisor.controller;

import com.example.tripadvisor.dto.FlightReservationRequest;
import com.example.tripadvisor.dto.FlightReservationResponse;
import com.example.tripadvisor.dto.TripPlan;
import com.example.tripadvisor.service.ReservationService;
import com.example.tripadvisor.service.TripPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/trips")
@Slf4j
public class TripController {
    private final TripPlanService tripPlanService;
    private final ReservationService reservationService;

    @GetMapping("/{airportCode}")
    public TripPlan getTripPlan(@PathVariable String airportCode) {
        log.info("Airport Code: {} is Virtual: {}", airportCode, Thread.currentThread().isVirtual());
        return tripPlanService.getTripPlan(airportCode);
    }

    @PostMapping("/reserve")
    public FlightReservationResponse reserveFlight(@RequestBody FlightReservationRequest flightReservationRequest) {
        return reservationService.reserve(flightReservationRequest);
    }
}
