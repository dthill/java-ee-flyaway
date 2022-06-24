package pgfsd.filter;

import pgfsd.entities.User;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AdminFilter", urlPatterns = {"/flights-admin.jsp", "/users-admin.jsp"})
public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
        if (session == null ||
                session.getAttribute("logged-user") == null ||
                !((User)session.getAttribute("logged-user")).getAdmin()
        ) {
            request.setAttribute("login-error", "Please login as admin to view this page");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
