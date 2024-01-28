package com.amadeus.flightsearch.scheduler;

import com.amadeus.flightsearch.entities.Flight;
import com.amadeus.flightsearch.services.FlightService;
import com.amadeus.flightsearch.services.MockFlightService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FlightScheduledJob {
    private final FlightService flightService;
    private final MockFlightService mockFlightService;

    public FlightScheduledJob(FlightService flightService, MockFlightService mockFlightService) {
        this.flightService = flightService;
        this.mockFlightService = mockFlightService;
    }

    @Scheduled(cron = "0 0 3 * * * ")
    public void fetchAndSaveFlights() {
        List<Flight> mockFlights = mockFlightService.getMockFlights();
        flightService.createFlight(mockFlights);
    }
}
