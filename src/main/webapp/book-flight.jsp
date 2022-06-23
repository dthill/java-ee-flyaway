
<html>
<head>
    <title>Book Flight</title>
</head>
<body>
<h1>Book Flight</h1>
<h2>Flight details</h2>
<jsp:include page="/flight-booking-controller"></jsp:include>
<div>
    <%=session.getAttribute("booking-flight")%>
</div>
<%
    if(session.getAttribute("logged-user") == null){
        %>
<a href="login.jsp?flight=<%=request.getParameter("flight")%>">Login or register</a>
<%
    } else {
%>
<h2>Booking Information</h2>
<form action="book-flight.jsp" method="post">
    <input type="hidden" value="<%=request.getParameter("flight")%>" name="flight" id="flight">
    <label for="booking-quantity">Quantity of seats to book</label>
    <input type="number" min="1" value="1" id="booking-quantity" name="booking-quantity">
    <br>
    <h3>Total Price</h3>
    <p id="total-price"></p>
    <label for="booking-credit-card">Payment credit card</label><input type="tel" id="booking-credit-card" name="booking-credit-card">
    <br>
    <input type="submit" value="book now">
</form>

<%
    }
%>

<script>
    const priceString = document.querySelector("#flight-price").innerText;
    const price = parseInt(priceString, 10);

    const totalPriceElement = document.querySelector("#total-price");
    totalPriceElement.innerText = price;

    const priceUpdate = (event) => {
        totalPriceElement.innerText = price * parseInt(event.target.value);
    }
    document.querySelector("#booking-quantity").addEventListener("input", priceUpdate)
</script>
</body>
</html>
