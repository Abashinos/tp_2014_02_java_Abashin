package servlets;

import services.UserSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WaitServlet extends AbstractServlet {

    private Map<String, Integer> trials =  new HashMap<>();

    public WaitServlet() {
        setPage("wait.html");
    }

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        UserSession userSession = getSessionMap().get(sessionId);
        Map<String, Object> pageData = new HashMap<>();

        if (!trials.containsKey(Thread.currentThread().getName())) {
            trials.put(Thread.currentThread().getName(), 0);
        }


        if ((userSession == null || userSession.getStatus() != UserSession.Status.OK) && trials.get(Thread.currentThread().getName()) < 3 ) {
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException ignored) {

            }
            finally {
                trials.put(Thread.currentThread().getName(), trials.get(Thread.currentThread().getName()) + 1);
                doGet(request, response);
            }
        }
        else if (trials.get(Thread.currentThread().getName()) >= 3) {
            trials.put(Thread.currentThread().getName(), 0);

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

    private synchronized void trialsIncrement() {
    }

}
