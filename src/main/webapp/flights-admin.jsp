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
        String addedAirline = (String) session.getAttribute("airline-added");
        if (addedAirline != null) {
            out.print(addedAirline);
        }

        String deletedAirline = (String) session.getAttribute("airline-deleted");
        if(deletedAirline != null){
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
        String addedDestination = (String) session.getAttribute("destination-added");
        if (addedDestination != null) {
            out.print(addedDestination);
        }

        String deletedDestination = (String) session.getAttribute("destination-deleted");
        if(deletedDestination != null){
            out.print(deletedDestination);
        }

        String destinations = (String) session.getAttribute("destinations");
        out.print(destinations);

    %>
</div>
</body>
</html>
