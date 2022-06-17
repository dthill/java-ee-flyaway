package pgfsd.service;

import pgfsd.dao.AirlineDao;
import pgfsd.entities.Airline;

import java.util.List;

public class AirlineService {
    private final AirlineDao airlineDao = new AirlineDao();

    public String getAllAirlines() {
        List<Airline> allAirlines = airlineDao.getAllAirlines();
        if (allAirlines == null || allAirlines.size() == 0) {
            return ("<p>No airlines found</p>");
        } else {
            StringBuilder result = new StringBuilder("<table><tr><td>code</td><td>name</td><td>delete airline</td></tr>");
            for (Airline airline : allAirlines) {
                String deleteForm = String.format("<form action=\"flights-admin.jsp\" method=\"post\">\n" +
                        "    <input type=\"hidden\" value=\"%s\" name=\"airline-delete\" id=\"destination-delete\">\n" +
                        "    <input type=\"submit\" value=\"delete\">\n" +
                        "</form>", airline.getCode());
                result.append("<tr>")
                        .append("<td>")
                        .append(airline.getCode())
                        .append("</td>")
                        .append("<td>")
                        .append(airline.getName())
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

    public String addAirline(Airline airline) {
        if (airline == null) {
            return null;
        }
        if (airline.getName() == null && airline.getCode() == null) {
            return null;
        }
        if (airline.getName().equals("") ||
                airline.getCode().equals("") ||
                airline.getCode().length() != 3) {
            return "Entered airline not valid. Provide an airline code of 3 letters and a required name.";
        }
        boolean success = airlineDao.addAirline(airline);
        if (success) {
            return "Airline added/edited successfully.";
        }
        return "An error occurred adding/editing the airline.";
    }

    public String deleteAirline(Airline airline){
        if(airline == null || airline.getCode() == null){
            return null;
        }
        boolean success = airlineDao.deleteAirline(airline);
        if(success){
            return "Airline has been removed.";
        }
        return "An error occurred deleting the airline.";
    }
}
