package pgfsd.service;

import pgfsd.dao.DestinationsDao;
import pgfsd.entities.Destination;

import java.util.List;

public class DestinationService {
    private final DestinationsDao destinationsDao = new DestinationsDao();

    public String getAllDestination() {
        List<Destination> allDestinations = destinationsDao.getAllDestinations();
        if (allDestinations == null || allDestinations.size() == 0) {
            return ("<p>No destinations found</p>");
        } else {
            StringBuilder result = new StringBuilder("<table><tr><td>code</td><td>name</td><td>delete destination</td></tr>");
            for (Destination destination : allDestinations) {
                String deleteForm = String.format("<form action=\"flights-admin.jsp\" method=\"post\">\n" +
                        "    <input type=\"hidden\" value=\"%s\" name=\"destination-delete\" id=\"destination-delete\">\n" +
                        "    <input type=\"submit\" value=\"delete\">\n" +
                        "</form>", destination.getCode());
                result.append("<tr>")
                        .append("<td>")
                        .append(destination.getCode())
                        .append("</td>")
                        .append("<td>")
                        .append(destination.getName())
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

    public String addDestination(Destination destination) {
        if (destination == null) {
            return null;
        }
        if (destination.getName() == null && destination.getCode() == null) {
            return null;
        }
        if (destination.getName().equals("") ||
                destination.getCode().equals("") ||
                destination.getCode().length() != 3) {
            return "Entered destination not valid. Provide a destination code of 3 letters and a required name.";
        }
        boolean success = destinationsDao.addDestination(destination);
        if (success) {
            return "Destination added/edited successfully.";
        }
        return "An error occurred adding/editing the destination.";
    }

    public String deleteDestination(Destination destination) {
        if (destination == null || destination.getCode() == null) {
            return null;
        }
        boolean success = destinationsDao.deleteDestination(destination);
        if (success) {
            return "Destination has been removed.";
        }
        return "An error occurred deleting the destination. Check there are no flights requiring this destination";
    }
}
