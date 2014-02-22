package servlets;

import generator.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Frontend extends HttpServlet{

    private final static Map<String, String> VALID_DATA = new HashMap<>();
    private AtomicLong userIdGenerator = new AtomicLong();

    static {
        VALID_DATA.put("test_name", "test_pass");
        VALID_DATA.put("test_name2", "test_pass2");
    }

    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String page = "index.html";
        Map<String, Object> pageVars = new HashMap<>();

        switch(request.getPathInfo()) {
            case "/index":
            {
                page = "index.html";
                Redirector.redirect(request, response, pageVars);
                break;
            }
            case "/login":
            {
                page = "login.html";
                break;
            }
            case "/timer":
            {
                page = "timer.html";
                Redirector.redirect(request, response, pageVars);
                break;
            }

        }
        response.getWriter().println(PageGenerator.getPage(page, pageVars));

    }

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestUsername = request.getParameter("username");
        String requestPassword = request.getParameter("password");
        boolean isValid = VALID_DATA.containsKey(requestUsername) && VALID_DATA.get(requestUsername).equals(requestPassword);

        Map <String, Object> pageVars = new HashMap<>();
        if (isValid) {
            long userId = userIdGenerator.getAndIncrement();
            request.getSession().setAttribute("userId", userId);
            response.sendRedirect("/timer");
        } else {
            pageVars.put("errorMessage", "Invalid username and/or password. Try again.");
            response.getWriter().println(PageGenerator.getPage("login.html", pageVars));
        }
    }
}
