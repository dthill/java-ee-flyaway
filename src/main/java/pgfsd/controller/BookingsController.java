package pgfsd.controller;

import pgfsd.entities.User;
import pgfsd.service.BookingService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "BookingsController", value = "/bookings-controller")
public class BookingsController extends HttpServlet {
    BookingService bookingService = new BookingService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        User loggedUser = (User) httpSession.getAttribute("logged-user");
        if(loggedUser == null){
            httpSession.setAttribute("booking-success", "Not a logged in user");
        } else {
            httpSession.setAttribute("bookings", bookingService.getAllBookings(loggedUser));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
