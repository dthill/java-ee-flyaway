package pgfsd.controller;

import pgfsd.entities.User;
import pgfsd.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RegisterUserController", value = "/register-user-controller")
public class RegisterUserController extends HttpServlet {
    UserService userService = new UserService();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String nameInput = request.getParameter("name");
        String emailInput = request.getParameter("email");
        String passwordInput = request.getParameter("password");
        User user = new User(emailInput,passwordInput, nameInput, false);
        String registered = userService.registerNerUser(user);
        if(registered == null){
            User registeredUser = userService.checkUserCredentials(user);
            if(registeredUser != null){
                session.setAttribute("logged-user", registeredUser);
                String flightId = request.getParameter("flight");
                if( flightId != null && !flightId.equals("")){
                    response.sendRedirect("book-flight.jsp?flight=" + flightId);
                } else {
                    response.sendRedirect("index.jsp");
                }
                return;
            } else {
                request.setAttribute("registration-error", "Unable to register new user");
            }
        } else {
            request.setAttribute("registration-error", registered);
        }
        request.getRequestDispatcher("/login.jsp").forward(request,response);

    }
}
