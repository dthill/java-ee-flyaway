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
    <select id="departure-destination" name="departure-destination">
        <%
            String destinationOptions = (String) session.getAttribute("destination-options");
            out.print(destinationOptions);
        %>
    </select>
    <label for="departure-date">Departure Date</label>
    <input type="datetime-local" id="departure-date" name="departure-date">
    <br>
    <label for="arrival-destination">Arrival</label>
    <select id="arrival-destination" name="arrival-destination">
        <%
            out.print(destinationOptions);
        %>
    </select>
    <label for="arrival-date">Departure Date</label>
    <input type="datetime-local" id="arrival-date" name="arrival-date">
    <br>
    <input type="submit" value="Find Flight">
</form>
<p>
    <%
        String searchError = (String) session.getAttribute("search-error");
        if(searchError != null){
            out.print(searchError);
        }
    %>
</p>
<br/>
<a href="flights-admin.jsp">Flight Administration</a>
<a href="users-admin.jsp">Users Administration</a>

</body>
</html>