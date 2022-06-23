package pgfsd.controller;

import pgfsd.entities.User;
import pgfsd.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginController", value = "/login-controller")
public class LoginController extends HttpServlet {
    UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        User userCheck = userService.checkUserCredentials(new User(
                request.getParameter("email"),
                request.getParameter("password"),
                null,
                null));
        if(userCheck != null){
            httpSession.setAttribute("logged-user", userCheck);
            String flightId = request.getParameter("flight");
            if( flightId != null && !flightId.equals("")){
                request.getServletContext()
                        .getRequestDispatcher("/book-flight.jsp?flight=" + flightId)
                        .forward(request,response);
            } else {
                request.getServletContext()
                        .getRequestDispatcher("/index.jsp")
                        .forward(request,response);
            }
        } else {
            httpSession.setAttribute("login-error", "Login failed");
        }
    }
}
