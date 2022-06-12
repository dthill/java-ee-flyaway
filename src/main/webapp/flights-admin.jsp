<%@ page import="java.io.PrintWriter" %>
<html>
<head>
    <title>Flights Administration</title>
</head>
<body>
<jsp:include page="/flight-admin-controller"></jsp:include>
<h1>Manage Flights</h1>
<h2>Destinations</h2>
<%
    PrintWriter pw = response.getWriter();
    String destinations = (String) session.getAttribute("destinations");
    pw.println(destinations);
%>
</body>
</html>
