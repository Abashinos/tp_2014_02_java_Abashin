package servlets;

import services.UserSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static supplies.ResponseGenerator.setSuccessData;

public abstract class AuthServlet extends AbstractMServlet {


    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        Map<String, Object> pageData = new HashMap<>();

        if (sessionId != null) {
            UserSession userSession = getSessionMap().get(sessionId);

            if (userSession != null && userSession.getErrorMessage() != null) {
                pageData.put("userId", userSession.getUserId());
                pageData.put("errorMessage", userSession.getErrorMessage());
            }
        }

        setSuccessData(response);
        response.getWriter().println(PageGenerator.getPage(getPage(), pageData));
    }
}
