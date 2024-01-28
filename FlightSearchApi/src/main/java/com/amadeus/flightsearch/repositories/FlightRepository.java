package com.amadeus.flightsearch.repositories;

import com.amadeus.flightsearch.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByDepartureAirportCodeAndArrivalAirportCodeAndDepartureTime(String departureAirportCode, String arrivalAirportCode, LocalDateTime departureTime);

    List<Flight> findByDepartureAirportCodeAndArrivalAirportCodeAndDepartureTimeAndReturnTime(String departureAirportCode, String arrivalAirportCode, LocalDateTime departureTime, LocalDateTime returnTime);
}
