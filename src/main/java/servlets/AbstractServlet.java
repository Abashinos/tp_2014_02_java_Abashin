package servlets;

import resources.PagesData;
import resources.resource_system.ResourceFactory;
import services.UserSession;

import javax.servlet.http.HttpServlet;
import java.util.Map;

public abstract class AbstractServlet extends HttpServlet {

    private String page;
    protected static PagesData pagesData = null;
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

    public synchronized void setPagesData() {
        if (pagesData == null) {
            pagesData = (PagesData) ResourceFactory.getInstance().get("pages");
        }
    }

    protected synchronized void setPage(String page) {
        this.page = page;
    }

}
