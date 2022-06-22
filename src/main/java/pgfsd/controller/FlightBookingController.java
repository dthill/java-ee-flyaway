package pgfsd.controller;

import pgfsd.entities.Booking;
import pgfsd.entities.User;
import pgfsd.service.BookingService;
import pgfsd.service.FlightService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "FlightBookingController", value = "/flight-booking-controller")
public class FlightBookingController extends HttpServlet {
    FlightService flightService = new FlightService();
    BookingService bookingService = new BookingService();

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
        HttpSession httpSession = request.getSession();
        String bookingQuantityInput = request.getParameter("booking-quantity");
        String creditCardInput = request.getParameter("booking-credit-card");

        boolean paid = false;
        try{
            Long creditCard = Long.parseLong(creditCardInput);
            if(creditCard > 0){
                paid = true;
            }
        } catch (NumberFormatException e){
            System.out.println("Credit card not valid: " + creditCardInput);
        }
        User user = (User) httpSession.getAttribute("logged-user");
        String success = bookingService.deleteBooking(new Booking())

    }
}
