<html>
<head>
    <title>Login/Register</title>
</head>
<body>
<h1>Login or Register</h1>
<h2>Login</h2>
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

<h2>Register</h2>
<form action="register-user-controller" method="post">
    <input type="hidden" value="<%=request.getParameter("flight")%>" name="flight" id="flight">
    <label for="name">Name</label>
    <br>
    <input type="text" id="name" name="name">
    <br>
    <label for="email">Email</label>
    <br>
    <input type="email" id="email" name="email">
    <br>
    <label for="password">Password</label>
    <br>
    <input type="password" id="password" name="password">
    <br>
    <input type="submit" value="Register">
</form>
<p>
    <%
        String registerError = (String) request.getAttribute("registration-error");
        if(registerError != null){
            out.print(registerError);
        }
    %>
</p>
</body>
</html>
