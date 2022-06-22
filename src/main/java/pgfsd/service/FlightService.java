package pgfsd.service;

import pgfsd.dao.AirlineDao;
import pgfsd.dao.DestinationsDao;
import pgfsd.dao.FlightDao;
import pgfsd.entities.Airline;
import pgfsd.entities.Destination;
import pgfsd.entities.Flight;

import java.util.Date;
import java.util.List;


public class FlightService {
    private final FlightDao flightDao = new FlightDao();
    private final AirlineDao airlineDao = new AirlineDao();
    private final DestinationsDao destinationsDao = new DestinationsDao();

    public String getAllFlights() {
        List<Flight> allFlights = flightDao.getAllFlights();
        if (allFlights == null || allFlights.size() == 0) {
            return ("<p>No flights found</p>");
        } else {
            StringBuilder result = new StringBuilder("<table><tr><td>Id</td><td>departure</td><td>departure date</td><td>arrival</td><td>arrival date</td><td>price</td><td>delete flight</td></tr>");
            for (Flight flight : allFlights) {
                String deleteForm = String.format("<form action=\"flights-admin.jsp\" method=\"post\">\n" +
                        "    <input type=\"hidden\" value=\"%s\" name=\"flight-delete\" id=\"flight-delete\">\n" +
                        "    <input type=\"submit\" value=\"delete\">\n" +
                        "</form>", flight.getId().toString());
                result.append("<tr>")
                        .append("<td>")
                        .append(flight.getId())
                        .append("</td>")
                        .append("<td>")
                        .append(flight.getDepartureDestination().getName())
                        .append("</td>")
                        .append("<td>")
                        .append(flight.getDepartureDate().toString())
                        .append("</td>")
                        .append("<td>")
                        .append(flight.getArrivalDestination().getName())
                        .append("</td>")
                        .append("<td>")
                        .append(flight.getArrivalDate().toString())
                        .append("</td>")
                        .append("<td>")
                        .append(flight.getPrice())
                        .append("</td>")
                        .append("<td>")
                        .append(deleteForm)
                        .append("</td>")
                        .append("</tr>");
            }
            result.append("</table>");
            return result.toString();
        }
    }

    public String addFlight(Flight flight) {
        if (flight == null || nullFlight(flight)) {
            return null;
        }
        if (flight.getId() == null) {
            return "Entered Flight not valid. Provide a valid flight id.";
        }
        if (flight.getDepartureDestination() == null) {
            return "Entered Flight not valid. Provide a valid departure destination.";
        }
        if (flight.getArrivalDestination() == null ||
                flight.getArrivalDestination().equals(flight.getDepartureDestination())) {
            return "Entered Flight not valid. Provide a valid arrival destination different from the departure destination.";
        }
        if (flight.getDepartureDate() == null ||
                flight.getDepartureDate().before(new Date(System.currentTimeMillis()))) {
            return "Entered Flight not valid. Provide a valid departure date not in the past.";
        }
        if (flight.getArrivalDate() == null || flight.getArrivalDate().before(flight.getDepartureDate())) {
            return "Entered Flight not valid. Provide a valid arrival date date after the departure date.";
        }
        if (flight.getPrice() == null || flight.getPrice() <= 0) {
            return "Entered Flight not valid. Provide a valid price > 0.";
        }
        boolean success = flightDao.addFlight(flight);
        if (success) {
            return "Flight added/edited successfully.";
        }
        return "An error occurred adding/editing the flight. Check the flight id is not taken already.";
    }

    public String deleteDestination(Flight flight) {
        if (flight == null || nullFlight(flight)) {
            return null;
        }
        boolean success = flightDao.deleteFlight(flight);
        if (success) {
            return "Flight has been removed.";
        }
        return "An error occurred deleting the flight. Check there are no bookings associated with this flights";
    }

    public String getAirlineOptions() {
        List<Airline> airlines = airlineDao.getAllAirlines();
        StringBuilder result = new StringBuilder();
        for (Airline airline : airlines) {
            result.append("<option value=\"")
                    .append(airline.getCode())
                    .append("\">")
                    .append(airline.getName())
                    .append("</option>");
        }
        return result.toString();
    }

    public String getDestinationOptions() {
        List<Destination> destinations = destinationsDao.getAllDestinations();
        StringBuilder result = new StringBuilder();
        for (Destination destination : destinations) {
            result.append("<option value=\"")
                    .append(destination.getCode())
                    .append("\">")
                    .append(destination.getName())
                    .append("</option>");
        }
        return result.toString();
    }

    public String canSearchFlight(Flight flight) {
        if (flight == null) {
            return "No valid Flight provided";
        }
        if (flight.getDepartureDate() == null) {
            return "No valid flight provided. Provide a valid departure date not in the past.";
        }
        if (flight.getDepartureDestination() == null ||
                flight.getDepartureDestination().equals("")) {
            return "No valid flight provided. Provide a valid departure destination.";
        }
        if (flight.getArrivalDestination() == null ||
                flight.getArrivalDestination().equals("") ||
                flight.getArrivalDestination().equals(flight.getDepartureDestination())) {
            return "No valid flight provided. Provide a valid arrival destination different from the departure destination.";
        }
        return null;
    }

    public String searchFlights(Flight searchFlight){
        List<Flight> matchingFlights = flightDao.searchFlights(searchFlight);
        if(matchingFlights.size() == 0){
            return "<p>No matching flights found</p>";
        }
        StringBuilder result = new StringBuilder("<table><tr><td>From</td><td>To</td><td>departure date</td><td>arrival date</td><td>price</td><td>book</td></tr>");
        for (Flight flight : matchingFlights) {
            String bookLink = String.format("<a href=\"book-flight.jsp?flight=%s\">book</a>", flight.getId().toString());
            result.append("<tr>")
                    .append("<td>")
                    .append(flight.getDepartureDestination().getName())
                    .append("</td>")
                    .append("<td>")
                    .append(flight.getArrivalDestination().getName())
                    .append("</td>")
                    .append("<td>")
                    .append(flight.getDepartureDate().toString())
                    .append("</td>")
                    .append("<td>")
                    .append(flight.getArrivalDate().toString())
                    .append("</td>")
                    .append("<td>")
                    .append(flight.getPrice())
                    .append("</td>")
                    .append("<td>")
                    .append(bookLink)
                    .append("</td>")
                    .append("</tr>");
        }
        result.append("</table>");
        return result.toString();
    }

    public String getFlightById(String id){
        if(id == null || id.equals("")){
            return null;
        }
        Flight flight = flightDao.getFlightById(id);
        if(flight == null){
            return null;
        }
        StringBuilder result = new StringBuilder("<table><tr><td>From</td><td>To</td><td>departure date</td><td>arrival date</td><td>price</td></tr>")
                .append("<tr>")
                .append("<td>")
                .append(flight.getDepartureDestination().getName())
                .append("</td>")
                .append("<td>")
                .append(flight.getArrivalDestination().getName())
                .append("</td>")
                .append("<td>")
                .append(flight.getDepartureDate().toString())
                .append("</td>")
                .append("<td>")
                .append(flight.getArrivalDate().toString())
                .append("</td>")
                .append("<td id=\"flight-price\">")
                .append(flight.getPrice())
                .append("</td>")
                .append("</tr>")
                .append("</table>");
        return result.toString();
    }

    private boolean nullFlight(Flight flight) {
        return flight.getId() == null &&
                flight.getDepartureDestination() == null &&
                flight.getAirline() == null &&
                flight.getArrivalDate() == null &&
                flight.getArrivalDestination() == null &&
                flight.getDepartureDate() == null &&
                flight.getPrice() == null;
    }
}
