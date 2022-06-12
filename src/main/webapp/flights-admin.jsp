<html>
<head>
    <title>Flights Administration</title>
</head>
<body>
<jsp:include page="/flight-admin-controller"></jsp:include>
<h1>Manage Flights</h1>
<h2>Destinations</h2>
<div>
<%
    String destinations = (String) session.getAttribute("destinations");
    out.print(destinations);
%>
</div>
</body>
</html>
