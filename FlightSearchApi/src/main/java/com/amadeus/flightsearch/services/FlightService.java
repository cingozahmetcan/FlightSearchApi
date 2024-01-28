package com.amadeus.flightsearch.services;

import com.amadeus.flightsearch.entities.Flight;
import com.amadeus.flightsearch.repositories.FlightRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(Long flightId) {
        return flightRepository.findById(flightId).orElse(null);
    }

    public List<Flight> createFlight(List<Flight> flights) {
        List<Flight> savedFlights = flightRepository.saveAll(flights);
        List<Flight> returnFlights = new ArrayList<>();
        for (Flight flight : savedFlights) {
            if (flight.getReturnTime() != null) {
                Flight returnFlight = new Flight();
                returnFlight.setDepartureAirport(flight.getArrivalAirport());
                returnFlight.setArrivalAirport(flight.getDepartureAirport());
                returnFlight.setDepartureTime(flight.getReturnTime());
                returnFlight.setPrice(flight.getPrice());
                returnFlights.add(returnFlight);
            }
        }

        if (!returnFlights.isEmpty()) {
            flightRepository.saveAll(returnFlights);
        }
        return savedFlights;
    }

    public Flight updateFlightById(Long flightId, Flight flight) {
        Optional<Flight> flightToUpdate = flightRepository.findById(flightId);
        if(flightToUpdate.isPresent()) {
            flightToUpdate.get().setArrivalAirport(flight.getArrivalAirport());
            flightToUpdate.get().setDepartureAirport(flight.getDepartureAirport());
            flightToUpdate.get().setDepartureTime(flight.getDepartureTime());
            flightToUpdate.get().setReturnTime(flight.getReturnTime());
            flightToUpdate.get().setPrice(flight.getPrice());
            flightRepository.save(flightToUpdate.get());
        }
        return null;
    }

    public void deleteFlightById(Long flightId) {
        Optional<Flight> flightToDelete = flightRepository.findById(flightId);
        flightRepository.delete(flightToDelete.get());
    }

    public List<Flight> searchOneWayFlights(String departureAirportCode, String arrivalAirportCode, LocalDateTime departureTime) {
        return flightRepository.findByDepartureAirportCodeAndArrivalAirportCodeAndDepartureTime(departureAirportCode, arrivalAirportCode, departureTime);
    }

    public List<Flight> searchRoundTripFlights(String departureAirportCode, String arrivalAirportCode, LocalDateTime departureTime, LocalDateTime returnTime) {
        List<Flight> departureFlights = searchOneWayFlights(departureAirportCode, arrivalAirportCode, departureTime);
        List<Flight> returnFlights = new ArrayList<>();
        departureFlights.addAll(returnFlights);
        if(returnTime != null) {
            List<Flight> swappedFlights = searchOneWayFlights(arrivalAirportCode, departureAirportCode, returnTime);
            returnFlights.addAll(swappedFlights);
        }
        List<Flight> allFlights = new ArrayList<>();
        allFlights.addAll(departureFlights);
        allFlights.addAll(returnFlights);
        return allFlights;

    }

}