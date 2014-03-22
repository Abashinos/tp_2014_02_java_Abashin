package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontendServlet extends AbstractServlet {

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = (Long) request.getSession().getAttribute("userId");

        response.setContentType(SUCCESS_CONTENT_TYPE);
        response.setStatus(SUCCESS_STATUS);
        page = request.getRequestURI().substring(1) + ".html";

        if (userId == null) {
            response.sendRedirect("/login");
        }
        else {
            pageVars.put("userId", userId);
            response.getWriter().println(PageGenerator.getPage(page, pageVars));
        }
    }

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
