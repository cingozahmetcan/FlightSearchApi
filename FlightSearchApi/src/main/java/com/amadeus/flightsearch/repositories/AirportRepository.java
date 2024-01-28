package com.amadeus.flightsearch.repositories;

import com.amadeus.flightsearch.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
}
