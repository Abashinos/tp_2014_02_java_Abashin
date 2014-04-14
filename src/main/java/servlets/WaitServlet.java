package servlets;

import org.apache.commons.io.FilenameUtils;
import services.UserSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WaitServlet extends AbstractServlet {

    private int trials = 0;

    public WaitServlet() {
        setPage("wait.html");
    }

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        UserSession userSession = getSessionMap().get(sessionId);

        if ((userSession == null || userSession.getStatus() != UserSession.Status.OK) && trials < 3 ) {
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException ignored) {

            }
            finally {
                ++trials;
                response.getWriter().println(PageGenerator.getPage(getPage(), getPageVars()));
                doGet(request, response);
            }
        }
        else if (trials >= 3) {
            trials = 0;

            String referrer = request.getHeader("referer");
            if (referrer != null) {
                String extension = referrer.substring(referrer.lastIndexOf('/') + 1, referrer.length());
                response.sendRedirect("/" + extension);
            }
            else
                response.sendRedirect("/login");
        }
        else {
            response.sendRedirect("/timer");
        }

    }

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

}
