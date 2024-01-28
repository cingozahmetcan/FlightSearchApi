package com.amadeus.flightsearch.services;

import com.amadeus.flightsearch.entities.Airport;
import com.amadeus.flightsearch.entities.Flight;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MockFlightService {

    public List<Flight> getMockFlights() {
        List<Flight> flights = new ArrayList<>();
        // add mock flights
        return  flights;
    }
}
