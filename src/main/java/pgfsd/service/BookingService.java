package pgfsd.service;

import pgfsd.dao.AirlineDao;
import pgfsd.dao.BookingDao;
import pgfsd.entities.Airline;
import pgfsd.entities.Booking;
import pgfsd.entities.User;

import java.util.List;

public class BookingService {
    private final BookingDao bookingDao = new BookingDao();

    public String getAllBookings(User user) {
        List<Booking> allBookings = bookingDao.getAllBookings(user);
        if (allBookings == null || allBookings.size() == 0) {
            return ("<p>No Bookings found</p>");
        } else {
            StringBuilder result = new StringBuilder("<table><tr><td>id</td><td>name</td><td>delete airline</td></tr>");
            for (Booking booking : allBookings) {
                result.append("<tr>")
                        .append("<td>")
                        .append(booking.getId())
                        .append("</td>")
                        .append("<td>")
                        .append(booking.getFlight())
                        .append("</td>")
                        .append("<td>")
                        .append(booking.getSeatQuantity())
                        .append("</td>")
                        .append("</tr>");
            }
            result.append("</table>");
            return result.toString();
        }
    }

    public String addBooking(Booking booking) {
        if (booking == null) {
            return "Booking not valid";
        }
        if(booking.getUser() == null){
            return "Booking not valid. No user provided.";
        }
        if(booking.getFlight() == null){
            return "Booking not valid. No flight provided.";
        }
        if(booking.getSeatQuantity() == null){
            return "Booking not valid. No valid seat quantity provided.";
        }
        if(!booking.getPaid()){
            return "Booking not valid. No valid payment provided.";
        }

        boolean success = bookingDao.addBooking(booking);
        if (success) {
            return null;
        }
        return "An error occurred adding/editing the booking.";
    }

    public String deleteBooking(Booking booking) {
        if (booking == null || booking.getId() == null) {
            return null;
        }
        boolean success = bookingDao.deleteBooking(booking);
        if (success) {
            return "Booking has been removed.";
        }
        return "An error occurred deleting the booking.";
    }
}
