package servlets;

import services.UserSession;

import javax.servlet.http.HttpServlet;
import java.util.Map;

public abstract class AbstractServlet extends HttpServlet {

    private String page;
    protected Map<String, UserSession> sessionMap;


    public String getPage() {
        return page;
    }

    public Map<String, UserSession> getSessionMap() {
        return sessionMap;
    }
    public synchronized void setSessionMap(Map<String, UserSession> sessionMap) {
        this.sessionMap = sessionMap;
    }
    public synchronized void setSession(UserSession session) {
        UserSession userSession = getSessionMap().get(session.getSessionId());
        if (userSession != null) {
            userSession.setUserSession(session);
        }
    }

    protected synchronized void setPage(String page) {
        this.page = page;
    }

}
