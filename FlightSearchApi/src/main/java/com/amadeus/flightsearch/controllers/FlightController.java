package com.amadeus.flightsearch.controllers;

import com.amadeus.flightsearch.entities.Flight;
import com.amadeus.flightsearch.services.FlightService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;


    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/{flightId}")
    public Flight getFlightById(@PathVariable Long flightId) {
        return flightService.getFlightById(flightId);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Flight>> searchFlights(@RequestParam String departureAirportCode,
                                                      @RequestParam String arrivalAirportCode,
                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime departureTime,
                                                      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime returnTime) {
        if(returnTime == null) {
            List<Flight> oneWayFlights = flightService.searchOneWayFlights(departureAirportCode, arrivalAirportCode, departureTime);
            return ResponseEntity.ok(oneWayFlights);
        }
        else {
            List<Flight> roundTripFlights = flightService.searchRoundTripFlights(departureAirportCode, arrivalAirportCode, departureTime, returnTime);
            return ResponseEntity.ok(roundTripFlights);
        }

    }

    @PostMapping
    public List<Flight> createFlight(@RequestBody Flight flight) {
        List<Flight> flights = new ArrayList<>();
        flights.add(flight);
        if(flight.getReturnTime() != null) {
            Flight returnFlight = new Flight();
            returnFlight.setDepartureAirport(flight.getArrivalAirport());
            returnFlight.setArrivalAirport(flight.getDepartureAirport());
            returnFlight.setDepartureTime(flight.getReturnTime());
            returnFlight.setPrice(flight.getPrice());
            flights.add(returnFlight);
        }
        return flightService.createFlight(flights);
    }


    @PutMapping("/{flightId}")
    public Flight updateFlightById(@PathVariable Long flightId, @RequestBody Flight flight) {
        return flightService.updateFlightById(flightId, flight);
    }

    @DeleteMapping("/{flightId}")
    public void deleteFlightById(@PathVariable Long flightId) {
        flightService.deleteFlightById(flightId);
    }
}
