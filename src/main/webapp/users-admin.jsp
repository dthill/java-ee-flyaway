<html>
<head>
    <title>Users Administration</title>
</head>
<body>

<jsp:include page="/user-admin-controller"></jsp:include>
<h1>Users Administration</h1>
<form action="users-admin.jsp" method="post">
    <label for="name">Name</label>
    <input type="text" id="name" name="name">
    <label for="email">Email</label>
    <input type="email" id="email" name="email">
    <label for="password">Password</label>
    <input type="password" id="password" name="password">
    <label for="admin">Admin</label>
    <input type="checkbox" id="admin" name="admin">
    <input type="submit" value="Save/edit user">
</form>
<div>
    <%
        String userAdded = (String) request.getAttribute("user-added");
        if (userAdded != null) {
            System.out.println(userAdded);
        }

        String userDeleted = (String) request.getAttribute("user-deleted");
        if (userDeleted != null) {
            System.out.println(userDeleted);
        }
    %>
</div>
<div>
    <%
        String users = (String) session.getAttribute("users");
        out.print(users);
    %>
</div>
</body>
</html>
