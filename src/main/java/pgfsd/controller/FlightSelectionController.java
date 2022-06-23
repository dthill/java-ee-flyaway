package pgfsd.controller;

import pgfsd.entities.Destination;
import pgfsd.entities.Flight;
import pgfsd.service.FlightService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "FlightSelectionController", value = "/flight-selection-controller")
public class FlightSelectionController extends HttpServlet {
    FlightService flightService = new FlightService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("destination-options", flightService.getDestinationOptions());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String departureDestinationInput = request.getParameter("departure-destination");
        String arrivalDestinationInput = request.getParameter("arrival-destination");
        String departureDateInput = request.getParameter("departure-date");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Destination departureDestination = new Destination(departureDestinationInput, "");
        Destination arrivalDestination = new Destination(arrivalDestinationInput, "");
        Date departureDate = null;

        try {
            departureDate = formatter.parse(departureDateInput);
        } catch (Exception e) {
            System.out.println("Departure date not valid: " + departureDateInput);
            System.out.println(e.getMessage());
        }

        Flight flight = new Flight(
                0L,
                null,
                departureDestination,
                departureDate,
                arrivalDestination,
                new Date(),
                0.0);
        String canSearchFlight = flightService.canSearchFlight(flight);
        HttpSession httpSession = request.getSession();
        if (canSearchFlight == null) {
            httpSession.setAttribute("matching-flights", flightService.searchFlights(flight));
            httpSession.setAttribute("departure-destination", departureDestinationInput);
            httpSession.setAttribute("arrival-destination", arrivalDestinationInput);
            httpSession.setAttribute("departure-date", departureDateInput);
        } else {
            request.setAttribute("search-error", canSearchFlight);
        }
        doGet(request, response);
    }
}
