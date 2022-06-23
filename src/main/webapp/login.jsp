<html>
<head>
    <title>Login/Register</title>
</head>
<body>
<h1>Login</h1>
<form action="login-controller" method="post">
    <input type="hidden" value="<%=request.getParameter("flight")%>" name="flight" id="flight">
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
        String loginError = (String) request.getAttribute("login-error");
        if(loginError != null){
            out.print(loginError);
        }
    %>
</p>
</body>
</html>
