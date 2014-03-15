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

    final String SUCCESS_CONTENT_TYPE = "text/html;charset=utf-8";
    final int SUCCESS_STATUS = HttpServletResponse.SC_OK;

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(SUCCESS_CONTENT_TYPE);
        response.setStatus(SUCCESS_STATUS);
        Map<String, Object> pageVars = new HashMap<>();

        Redirector.redirect(request, response, pageVars);

    }

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
