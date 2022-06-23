<%@ page import="pgfsd.entities.Flight" %>
<!DOCTYPE html>
<html>
<head>
    <title>Fly Away</title>
</head>
<body>
<h1>Fly Away</h1>
<h2>Book Flight</h2>
<jsp:include page="/flight-selection-controller"></jsp:include>
<form action="index.jsp" method="post">
    <label for="departure-destination">Departure</label>
    <select
            id="departure-destination"
            name="departure-destination"
    >
        <%
            String destinationOptions = (String) session.getAttribute("destination-options");
            out.print(destinationOptions);
        %>
    </select>
    <label for="arrival-destination">Arrival</label>
    <select
            id="arrival-destination"
            name="arrival-destination"
    >
        <%
            out.print(destinationOptions);
        %>
    </select>
    <label for="departure-date">Departure Date</label>
    <input
            type="date"
            id="departure-date"
            name="departure-date"
            value="<%=session.getAttribute("departure-date")%>"
    >
    <br>
    <input type="submit" value="Find Flight">
</form>
<p>
    <%
        String searchError = (String) session.getAttribute("search-error");
        if (searchError != null) {
            out.print(searchError);
        }
    %>
</p>
<br/>
<h2>
    <%
        Flight searchFlight = (Flight) session.getAttribute("search-flight");
        if (searchFlight != null) {
            out.println("Flights found");
        }
    %>
</h2>
<div>
    <%
        String matchingFlights = (String) session.getAttribute("matching-flights");
        if (matchingFlights != null) {
            out.print(matchingFlights);
        }
    %>
</div>
<br/>
<a href="flights-admin.jsp">Flight Administration</a>
<a href="users-admin.jsp">Users Administration</a>
<br>
<a href="login.jsp">Login</a>
<a href="logout-controller">Logout</a>
</body>
</html>