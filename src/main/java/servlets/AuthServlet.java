package servlets;

import services.UserSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static supplies.ResponseGenerator.setSuccessData;

public abstract class AuthServlet extends AbstractMServlet {


    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();

        removeFromPageVars("errorMessage");
        if (sessionId != null) {
            UserSession userSession = getSessionMap().get(sessionId);

            if (userSession != null && userSession.getErrorMessage() != null) {
                putInPageVars("userId", userSession.getUserId());
                putInPageVars("errorMessage", userSession.getErrorMessage());
            }
        }

        setSuccessData(response);
        response.getWriter().println(PageGenerator.getPage(getPage(), getPageVars()));
    }
}
