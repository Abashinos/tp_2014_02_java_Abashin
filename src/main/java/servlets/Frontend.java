package servlets;

import generator.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public static String getTime() {
        Date date = new Date();
        date.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH.mm.ss");
        return dateFormat.format(date);
    }

    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String page = "index.html";
        Map<String, Object> pageVars = new HashMap<>();

        switch(request.getPathInfo()) {
            case "/index":
            {
                HttpSession session = request.getSession();
                page = "index.html";
                Long userId = (Long) session.getAttribute("userId");
                if (userId == null) {
                    response.sendRedirect("/login");
                } else {
                    pageVars.put("userId", userId);
                }
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
                HttpSession session = request.getSession();
                Long userId = (Long) session.getAttribute("userId");
                if (userId == null) {
                    response.sendRedirect("/login");
                } else {
                    pageVars.put("userId", userId);
                }
                break;
            }

        }
        response.getWriter().println(PageGenerator.getPage(page, pageVars));

    }

    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestUsername = request.getParameter("username");
        String requestPassword = request.getParameter("password");
        Boolean isValid = VALID_DATA.containsKey(requestUsername) && VALID_DATA.get(requestUsername).equals(requestPassword);

        Map <String, Object> pageVars = new HashMap<>();
        if (isValid) {
            Long userId = userIdGenerator.getAndIncrement();
            request.getSession().setAttribute("userId", userId);
            response.sendRedirect("/timer");
        } else {
            pageVars.put("errorMessage", "Invalid username and/or password. Try again.");
            response.getWriter().println(PageGenerator.getPage("login.html", pageVars));
        }
    }
}
