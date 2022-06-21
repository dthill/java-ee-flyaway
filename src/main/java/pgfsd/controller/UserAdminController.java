package pgfsd.controller;

import pgfsd.entities.Airline;
import pgfsd.entities.User;
import pgfsd.service.AirlineService;
import pgfsd.service.DestinationService;
import pgfsd.service.FlightService;
import pgfsd.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UserAdminController", value = "/user-admin-controller")
public class UserAdminController extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService= new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("users", userService.getAllUsers());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();

        boolean admin = request.getParameter("admin") != null && request.getParameter("admin").equalsIgnoreCase("on");
        User user = new User(
                request.getParameter("email"),
                request.getParameter("password"),
                request.getParameter("name"),
                admin
                );
        String addedUser = userService.addUser(user);
        httpSession.setAttribute("user-added", addedUser);

        User deleteUser = new User(
                request.getParameter("user-delete"),
                "",
                "",
                null
        );
        String deletedUser = userService.deleteUser(deleteUser);
        httpSession.setAttribute("user-deleted", deletedUser);

        doGet(request,response);
    }
}
