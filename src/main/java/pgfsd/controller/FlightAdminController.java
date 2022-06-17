package pgfsd.controller;

import pgfsd.entities.Airline;
import pgfsd.entities.Destination;
import pgfsd.service.AirlineService;
import pgfsd.service.DestinationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/flight-admin-controller")
public class FlightAdminController extends HttpServlet {
    private DestinationService destinationService;
    private AirlineService airlineService;

    @Override
    public void init() throws ServletException {
        destinationService = new DestinationService();
        airlineService = new AirlineService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("destinations", destinationService.getAllDestination());
        httpSession.setAttribute("airlines", airlineService.getAllAirlines());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        doGet(request,response);
    }


}
