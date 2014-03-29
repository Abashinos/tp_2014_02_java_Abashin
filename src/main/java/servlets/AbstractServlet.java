package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractServlet extends HttpServlet {
    protected final static String SUCCESS_CONTENT_TYPE = "text/html;charset=utf-8";
    protected final static int SUCCESS_STATUS = HttpServletResponse.SC_OK;

    public Map<String, Object> getPageVars() {
        return pageVars;
    }

    public String getPage() {
        return page;
    }

    protected String page;
    protected Map<String, Object> pageVars = new HashMap<>();
    //TODO
}
