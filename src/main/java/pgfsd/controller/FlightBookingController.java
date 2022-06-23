package pgfsd.controller;

import pgfsd.dao.FlightDao;
import pgfsd.entities.Booking;
import pgfsd.entities.Flight;
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
        String flightDetails = flightService.getFlightDetails(request.getParameter("flight"));
        if(flightDetails == null){
            request.setAttribute("search-error", "Flight not found");
            request.getServletContext()
                    .getRequestDispatcher("/index.jsp")
                    .forward(request,response);
            return;
        }
        httpSession.setAttribute("booking-flight", flightDetails);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("email") != null ){
            doGet(request,response);
            return;
        }
        HttpSession httpSession = request.getSession();
        String bookingQuantityInput = request.getParameter("booking-quantity");
        String creditCardInput = request.getParameter("booking-credit-card");

        Flight flight = flightService.getFlightById(request.getParameter("flight"));
        if(flight == null){
            httpSession.setAttribute("search-error", "Flight id not valid");
            request.getServletContext()
                    .getRequestDispatcher("/index.jsp")
                    .forward(request,response);
            return;
        }
        User user = (User) httpSession.getAttribute("logged-user");
        Integer bookingQuantity = null;
        try{
            bookingQuantity = Integer.parseInt(bookingQuantityInput);
        } catch (Exception e){
            System.out.println("Booking quantity not valid :" + bookingQuantity);
        }
        boolean paid = false;
        try{
            Long creditCard = Long.parseLong(creditCardInput);
            if(creditCard > 0){
                paid = true;
            }
        } catch (NumberFormatException e){
            System.out.println("Credit card not valid: " + creditCardInput);
        }

        Booking booking = new Booking(flight, user, bookingQuantity, paid);
        String bookingAdded = bookingService.addBooking(booking);
        if(bookingAdded != null){
            request.setAttribute("booking-success", bookingAdded);
        } else {
            request.removeAttribute("booking-success");
            request.getRequestDispatcher("/bookings.jsp").forward(request,response);
        }

    }
}
