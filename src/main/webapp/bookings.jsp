
<html>
<head>
    <title>Bookings</title>
</head>
<body>
<jsp:include page="/bookings-controller"></jsp:include>
<h1>Bookings</h1>
<div>
    <%=session.getAttribute("booking-success")%>
    <%=session.getAttribute("bookings")%>
</div>
</body>
</html>
