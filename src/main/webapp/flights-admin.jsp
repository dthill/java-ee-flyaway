<html>
<head>
    <title>Flights Administration</title>
</head>
<body>
<jsp:include page="/flight-admin-controller"></jsp:include>
<h1>Manage Flights</h1>
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
        String added = (String) session.getAttribute("destination-added");
        if (added != null) {
            out.print(added);
        }

        String deleted = (String) session.getAttribute("destination-deleted");
        if(deleted != null){
            out.print(deleted);
        }

        String destinations = (String) session.getAttribute("destinations");
        out.print(destinations);

    %>
</div>
</body>
</html>
