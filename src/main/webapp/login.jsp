<html>
<head>
    <title>Login/Register</title>
</head>
<body>
<jsp:include page="/login-controller"></jsp:include>
<h1>Login</h1>
<form action="login.jsp" method="post">
    <label for="email">Email</label>
    <br>
    <input type="email" id="email" name="email">
    <br>
    <label for="password">Password</label>
    <br>
    <input type="password" id="password" name="password">
    <br>
    <input type="submit" value="login">
</form>
<p>
    <%
        String loginError = (String) session.getAttribute("login-error");
        if(loginError != null){
            out.print(loginError);
        }
    %>
</p>
</body>
</html>
