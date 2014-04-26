package servlets;

import services.UserSession;

import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractServlet extends HttpServlet {

    private String page;
    private Map<String, Object> pageVars = new HashMap<>();
    protected Map<String, UserSession> sessionMap;

    public Map<String, Object> getPageVars() {
        return pageVars;
    }

    public String getPage() {
        return page;
    }

    public Map<String, UserSession> getSessionMap() {
        return sessionMap;
    }
    public void setSessionMap(Map<String, UserSession> sessionMap) {
        this.sessionMap = sessionMap;
    }
    public void setSession(UserSession session) {
        UserSession userSession = getSessionMap().get(session.getSessionId());
        if (userSession != null) {
            userSession.setUserSession(session);
        }
    }

    protected Object putInPageVars(String key, Object value) {
        return pageVars.put(key, value);
    }
    protected Object removeFromPageVars(String key) {
        return pageVars.remove(key);
    }

    protected void setPage(String page) {
        this.page = page;
    }


}
