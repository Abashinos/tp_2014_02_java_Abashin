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

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        String page = "index.html";
        Map<String, Object> pageVars = new HashMap<>();

        Redirector.redirect(request, response, page, pageVars);

    }

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
