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
        httpSession.setAttribute("destination-options",flightService.getDestinationOptions());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String departureDestinationInput = request.getParameter("departure-destination");
        String departureDateInput = request.getParameter("departure-date");
        String arrivalDestinationInput = request.getParameter("arrival-destination");
        String arrivalDateInput = request.getParameter("arrival-destination");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Destination departureDestination = new Destination(departureDestinationInput, "");
        Date departureDate = null;
        Destination arrivalDestination = new Destination(arrivalDestinationInput, "");
        Date arrivalDate = null;
        try{
            departureDate = formatter.parse(departureDateInput);
        } catch (ParseException e){
            System.out.println("Departure date not valid: "+departureDateInput);
            System.out.println(e.getMessage());
        }
        try{
            arrivalDate = formatter.parse(arrivalDateInput);
        } catch (ParseException e){
            System.out.println("Arrival date not valid: "+arrivalDateInput);
            System.out.println(e.getMessage());
        }
        Flight flight = new Flight(
                0L,
                null,
                departureDestination,
                departureDate,
                arrivalDestination,
                arrivalDate,
                0.0);
        String canSearchFlight = flightService.canSearchFlight(flight);
        HttpSession httpSession = request.getSession()
;        if(canSearchFlight == null){
            httpSession.setAttribute("search-flight", flight);
            getServletContext()
                    .getRequestDispatcher("/flight-listing.jsp")
                    .forward(request, response);
        } else {
            httpSession.setAttribute("search-error", canSearchFlight);
            doGet(request,response);
        }
    }
}
