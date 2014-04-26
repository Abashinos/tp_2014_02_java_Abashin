package servlets;

import services.UserSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static supplies.ResponseGenerator.*;

public class FrontendServlet extends AbstractServlet {

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        UserSession userSession = getSessionMap().get(sessionId);
        Map<String, Object> pageData = new HashMap<>();

        setPage(request.getRequestURI().substring(1) + ".html");

        if (userSession == null || userSession.getStatus() != UserSession.Status.OK) {
            setRedirectData(response);
            response.sendRedirect("/login");
        }
        else {
            setSuccessData(response);
            pageData.put("userId", userSession.getUserId());
            pageData.put("sessionId", sessionId);
            response.getWriter().println(PageGenerator.getPage(getPage(), pageData));
        }
    }

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
