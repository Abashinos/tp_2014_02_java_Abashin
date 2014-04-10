package servlets;

import services.UserSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static supplies.ResponseGenerator.*;

public class FrontendServlet extends AbstractServlet {

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        UserSession userSession = getSessionMap().get(sessionId);

        setPage(request.getRequestURI().substring(1) + ".html");

        if (userSession == null || userSession.getStatus() != UserSession.Status.OK) {
            setRedirectData(response);
            response.sendRedirect("/login");
        }
        else {
            setSuccessData(response);
            putInPageVars("userId", userSession.getUserId());
            putInPageVars("sessionId", sessionId);
            response.getWriter().println(PageGenerator.getPage(getPage(), getPageVars()));
        }
    }

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
