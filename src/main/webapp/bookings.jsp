
<html>
<head>
    <title>Bookings</title>
</head>
<body>
<jsp:include page="/bookings-controller"></jsp:include>
<h1>Bookings</h1>
<div>
    <%
        String bookingSuccess = (String) request.getAttribute("booking-success");
        if (bookingSuccess != null) {
         out.print(bookingSuccess);
        }
    %>
    <%=session.getAttribute("bookings")%>
</div>
</body>
</html>
