package com.amadeus.flightsearch.controllers;

import com.amadeus.flightsearch.entities.Airport;
import com.amadeus.flightsearch.services.AirportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airports")
public class AirportController {
    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public List<Airport> getAllAirports() {
        return airportService.getAllAirports();
    }

    @GetMapping("/{airportId}")
    public Airport getAirport(@PathVariable Long airportId) {
        return airportService.getAirport(airportId);
    }

    @PostMapping
    public Airport createAirport(@RequestBody Airport airport) {
        return airportService.createAirport(airport);
    }

    @PutMapping("/{airportId}")
    public Airport updateAirportById(@PathVariable Long airportId, @RequestBody Airport airport) {
        return airportService.updateAirportById(airportId, airport);
    }

    @DeleteMapping("/{airportId}")
    public void deleteAirportById(@PathVariable Long airportId) {
        airportService.deleteAirportById(airportId);
    }

}
