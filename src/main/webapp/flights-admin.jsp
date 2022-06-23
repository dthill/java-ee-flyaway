<html>
<head>
    <title>Flights Administration</title>
</head>
<body>
<jsp:include page="/flight-admin-controller"></jsp:include>
<h1>Manage Flights</h1>
<h2>Airlines</h2>
<form action="flights-admin.jsp" method="post">
    <label for="airline-code">Code</label>
    <input type="text" name="airline-code" id="airline-code">
    <label for="airline-name">Name</label>
    <input type="text" name="airline-name" id="airline-name">
    <input type="submit" value="add/edit airline">
</form>
<div>
    <%
        String addedAirline = (String) request.getAttribute("airline-added");
        if (addedAirline != null) {
            out.print(addedAirline);
        }

        String deletedAirline = (String) request.getAttribute("airline-deleted");
        if (deletedAirline != null) {
            out.print(deletedAirline);
        }

        String airlines = (String) session.getAttribute("airlines");
        out.print(airlines);

    %>
</div>
<h2>Destinations</h2>
<form action="flights-admin.jsp" method="post">
    <label for="destination-code">Code</label>
    <input type="text" name="destination-code" id="destination-code">
    <label for="destination-name">Name</label>
    <input type="text" name="destination-name" id="destination-name">
    <input type="submit" value="add/edit destination">
</form>
<div>
    <%
        String addedDestination = (String) request.getAttribute("destination-added");
        if (addedDestination != null) {
            out.print(addedDestination);
        }

        String deletedDestination = (String) request.getAttribute("destination-deleted");
        if (deletedDestination != null) {
            out.print(deletedDestination);
        }

        String destinations = (String) session.getAttribute("destinations");
        out.print(destinations);

    %>
</div>
<h2>Flights</h2>
<form action="flights-admin.jsp" method="post">
    <label for="flight-id">Id</label>
    <input type="text" name="flight-id" id="flight-id">
    <label for="flight-airline">Airline</label>
    <select name="flight-airline" id="flight-airline">
        <%
            out.print((String) session.getAttribute("flight-airlines"));
        %>
    </select>
    <br>
    <label for="flight-departure-destination">Departure</label>
    <select name="flight-departure-destination" id="flight-departure-destination">
        <%
            out.print((String) session.getAttribute("flight-destinations"));
        %>
    </select>
    <label for="flight-arrival-destination">Departure</label>
    <select name="flight-arrival-destination" id="flight-arrival-destination">
        <%
            out.print((String) session.getAttribute("flight-destinations"));
        %>
    </select>
    <br>
    <label for="flight-departure-date">Departure Date</label>
    <input type="datetime-local" id="flight-departure-date" name="flight-departure-date">
    <label for="flight-arrival-date">Arrival Date</label>
    <input type="datetime-local" id="flight-arrival-date" name="flight-arrival-date">
    <br>
    <label for="flight-price">Price</label>
    <input type="text" name="flight-price" id="flight-price">
    <br>
    <input type="submit" value="add/edit flight">
</form>
<div>
    <%
        String addedFlight = (String) request.getAttribute("flight-added");
        if (addedFlight != null) {
            out.print(addedFlight);
        }

        String deletedFlight = (String) request.getAttribute("flight-deleted");
        if (deletedFlight != null) {
            out.print(deletedFlight);
        }

        String flights = (String) session.getAttribute("flights");
        out.print(flights);
    %>
</div>
</body>
</html>
