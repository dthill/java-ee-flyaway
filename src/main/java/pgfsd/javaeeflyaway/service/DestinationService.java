package pgfsd.javaeeflyaway.service;

import pgfsd.javaeeflyaway.dao.DestinationsDao;
import pgfsd.javaeeflyaway.entities.Destination;

import java.util.List;

public class DestinationService {
    private DestinationsDao destinationsDao = new DestinationsDao();

    public String getAllDestionation(){
        List<Destination> allDestinations = destinationsDao.getAllDestinations();
        if (allDestinations == null || allDestinations.size() == 0) {
            return ("<p>No destinations found</p>");
        } else {
            StringBuilder result = new StringBuilder("<table><tr><td>name</td><td>code</td></tr>");
            for (int i = 0; i < allDestinations.size(); i++) {
                Destination destination = allDestinations.get(i);
                result.append("<tr>")
                        .append("<td>")
                        .append(destination.getName())
                        .append("</td>")
                        .append("<td>")
                        .append(destination.getCode())
                        .append("</td>")
                        .append("</tr>");
            }
            result.append("</table>");
            return result.toString();
        }
    }
}
