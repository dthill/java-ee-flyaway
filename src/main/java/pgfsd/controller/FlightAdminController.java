package pgfsd.controller;

import pgfsd.entities.Airline;
import pgfsd.entities.Destination;
import pgfsd.entities.Flight;
import pgfsd.service.AirlineService;
import pgfsd.service.DestinationService;
import pgfsd.service.FlightService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/flight-admin-controller")
public class FlightAdminController extends HttpServlet {
    private DestinationService destinationService;
    private AirlineService airlineService;
    private FlightService flightService;

    @Override
    public void init() throws ServletException {
        destinationService = new DestinationService();
        airlineService = new AirlineService();
        flightService = new FlightService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("destinations", destinationService.getAllDestination());
        httpSession.setAttribute("airlines", airlineService.getAllAirlines());
        httpSession.setAttribute("flights", flightService.getAllFlights());
        httpSession.setAttribute("flight-airlines", flightService.getAirlineOptions());
        httpSession.setAttribute("flight-destinations", flightService.getDestinationOptions());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleAirlinesPost(request, response);
        handleDestinationsPost(request, response);
        handleFlightsDeletePost(request,response);
        handleFlightsAddPost(request, response);
        doGet(request, response);
    }

    private void handleAirlinesPost(HttpServletRequest request, HttpServletResponse response) {
        HttpSession httpSession = request.getSession();

        Airline airline = new Airline(
                request.getParameter("airline-code"),
                request.getParameter("airline-name"));
        String addedAirline = airlineService.addAirline(airline);
        httpSession.setAttribute("airline-added", addedAirline);

        Airline deleteAirline = new Airline(
                request.getParameter("airline-delete"),
                ""
        );
        String deletedAirline = airlineService.deleteAirline(deleteAirline);
        httpSession.setAttribute("airline-deleted", deletedAirline);
    }


    private void handleDestinationsPost(HttpServletRequest request, HttpServletResponse response) {
        HttpSession httpSession = request.getSession();

        Destination destination = new Destination(
                request.getParameter("destination-code"),
                request.getParameter("destination-name"));
        String addedDestination = destinationService.addDestination(destination);
        httpSession.setAttribute("destination-added", addedDestination);

        Destination deleteDestination = new Destination(
                request.getParameter("destination-delete"),
                ""
        );
        String deletedDestination = destinationService.deleteDestination(deleteDestination);
        httpSession.setAttribute("destination-deleted", deletedDestination);
    }

    private void handleFlightsAddPost(HttpServletRequest request, HttpServletResponse response) {
        HttpSession httpSession = request.getSession();

        String flightIdInput = request.getParameter("flight-id");
        String airlineCodeInput = request.getParameter("flight-airline");
        String departureCodeInput = request.getParameter("flight-departure-destination");
        String arrivalCodeInput = request.getParameter("flight-arrival-destination");
        String departureDateInput = request.getParameter("flight-departure-date");
        String arrivalDateInput = request.getParameter("flight-arrival-date");
        String priceInput = request.getParameter("flight-price");

        Long flightId = null;
        Airline airline = null;
        Destination departure = null;
        Destination arrival = null;
        Date departureDate = null;
        Date arrivalDate = null;
        Double price = null;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            flightId = Long.parseLong(flightIdInput);
        } catch (NumberFormatException e) {
            System.out.println("Flight add id provided not valid: " + flightIdInput);
            System.out.println(e.getMessage());
        }
        if (arrivalCodeInput != null && !airlineCodeInput.equals("")) {
            airline = new Airline(airlineCodeInput, "");
        }
        if (departureCodeInput != null && !departureCodeInput.equals("")) {
            departure = new Destination(departureCodeInput, "");
        }
        if (arrivalCodeInput != null && !arrivalCodeInput.equals("")) {
            arrival = new Destination(arrivalCodeInput, "");
        }
        try {
            if (departureDateInput == null) {
                throw  new ParseException(departureCodeInput, 0);
            }
            departureDate = formatter.parse(departureDateInput);
        } catch (ParseException e) {
            System.out.println("Departure date provided not valid: " + departureDateInput);
            System.out.println(e.getMessage());
        }
        try {
            if (arrivalDateInput == null) {
                throw  new ParseException(arrivalCodeInput, 0);
            }
            arrivalDate = formatter.parse(arrivalDateInput);
        } catch (ParseException e) {
            System.out.println("Arrival date provided not valid: " + arrivalDateInput);
            System.out.println(e.getMessage());
        }
        try {
            if(priceInput == null){
                throw new NumberFormatException();
            }
            price = Double.parseDouble(priceInput);
        } catch (NumberFormatException e) {
            System.out.println("Flight price provided not valid: " + flightIdInput);
            System.out.println(e.getMessage());

        }

        String flightAdded = flightService.addFlight(
                new Flight(
                        flightId,
                        airline,
                        departure,
                        departureDate,
                        arrival,
                        arrivalDate,
                        price
                ));
        httpSession.setAttribute("flight-added", flightAdded);



    }

    private void handleFlightsDeletePost(HttpServletRequest request, HttpServletResponse response) {
        HttpSession httpSession = request.getSession();

        String deleteFlightIdInput = request.getParameter("flight-delete");
        Long deleteFlightId = null;
        try {
            deleteFlightId = Long.parseLong(deleteFlightIdInput);
        } catch (NumberFormatException e) {
            System.out.println("Flight delete id provided not valid: " + deleteFlightIdInput);
            System.out.println(e.getMessage());
        }
        Flight deleteFlight = new Flight(deleteFlightId, null, null, new Date(), null, new Date(), 0.0);
        String deletedFlight = flightService.deleteDestination(deleteFlight);
        httpSession.setAttribute("flight-deleted", deletedFlight);
    }

}