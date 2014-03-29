package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static supplies.ResponseGenerator.*;

public class FrontendServlet extends AbstractServlet {

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = (Long) request.getSession().getAttribute("userId");

        setPage(request.getRequestURI().substring(1) + ".html");

        if (userId == null) {
            setRedirectData(response);
            response.sendRedirect("/login");
        }
        else {
            setSuccessData(response);
            putInPageVars("userId", userId);
            response.getWriter().println(PageGenerator.getPage(getPage(), getPageVars()));
        }
    }

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
