package pgfsd.javaeeflyaway.controller;

import pgfsd.javaeeflyaway.service.DestinationService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/flight-admin-controller")
public class FlightAdminController extends HttpServlet {
    private DestinationService destinationService;

    @Override
    public void init() throws ServletException {
        super.init();
        destinationService = new DestinationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("destinations", destinationService.getAllDestionation());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
