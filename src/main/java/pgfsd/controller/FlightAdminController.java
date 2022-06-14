package pgfsd.controller;

import pgfsd.entities.Destination;
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

    @Override
    public void init() throws ServletException {
        destinationService = new DestinationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("destinations", destinationService.getAllDestionation());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();

        Destination destination = new Destination(
                request.getParameter("destination-code"),
                request.getParameter("destination-name"));
        String added = destinationService.addDestination(destination);
        httpSession.setAttribute("destination-added", added);

        Destination deleteDestination = new Destination(
                request.getParameter("destination-delete"),
                ""
        );
        String deleted = destinationService.deleteDestination(deleteDestination);
        httpSession.setAttribute("destination-deleted", deleted);

        doGet(request,response);
    }


}
