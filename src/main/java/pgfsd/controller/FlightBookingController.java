package pgfsd.controller;

import pgfsd.service.FlightService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "FlightBookingController", value = "/flight-booking-controller")
public class FlightBookingController extends HttpServlet {
    FlightService flightService = new FlightService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        String flightDetails = flightService.getFlightById(request.getParameter("flight"));
        if(flightDetails == null){
            httpSession.setAttribute("search-error", "Flight not found");
            request.getServletContext()
                    .getRequestDispatcher("/index.jsp")
                    .forward(request,response);
            return;
        }
        httpSession.setAttribute("booking-flight", flightDetails);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookingQuantityInput = request.getParameter("booking-quantity");
        String creditCarInput = request.getParameter("booking-credit-card");

        // check there is a credit card
        //if no set error as session attribute and stay on page
        // if yes set save booking forward to manage booking page page
    }
}
