package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractServlet extends HttpServlet {

    private String page;
    private Map<String, Object> pageVars = new HashMap<>();

    public Map<String, Object> getPageVars() {
        return pageVars;
    }

    public String getPage() {
        return page;
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
