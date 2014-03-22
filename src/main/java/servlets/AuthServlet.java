package servlets;

import services.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServlet extends AbstractServlet {

    protected AccountService accountService;

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = (Long) request.getSession().getAttribute("userId");

        response.setContentType(SUCCESS_CONTENT_TYPE);
        response.setStatus(SUCCESS_STATUS);

        if (userId != null) {
            pageVars.put("userId", userId);
        }

        response.getWriter().println(PageGenerator.getPage(page, pageVars));
    }
}
