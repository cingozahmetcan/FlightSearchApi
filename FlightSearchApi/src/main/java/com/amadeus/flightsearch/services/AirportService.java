package com.amadeus.flightsearch.services;

import com.amadeus.flightsearch.entities.Airport;
import com.amadeus.flightsearch.repositories.AirportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportService {
    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public List<Airport> getAllAirports() {
       return airportRepository.findAll();
    }

    public Airport getAirport(Long airportId) {
        return airportRepository.findById(airportId).orElse(null);
    }

    public Airport createAirport(Airport airport) {
        return airportRepository.save(airport);
    }

    public Airport updateAirportById(Long airportId, Airport airport) {
        Optional<Airport> airportToUpdate = airportRepository.findById(airportId);
        if(airportToUpdate.isPresent()) {
            airportToUpdate.get().setCity(airport.getCity());
            airportToUpdate.get().setCode(airport.getCode());
            return airportRepository.save(airportToUpdate.get());
        }
        return null;
    }

    public void deleteAirportById(Long airportId) {
        Optional<Airport> airportToDelete = airportRepository.findById(airportId);
        airportToDelete.ifPresent(airportRepository::delete);
    }
}
