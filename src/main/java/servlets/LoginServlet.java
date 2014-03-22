package servlets;

import exceptions.AccountServiceException;
import services.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends AuthServlet {

    private final Map<String, Object> pageVars = new HashMap<>();

    public LoginServlet(AccountService accountService) {
        this.page = "login.html";
        this.accountService = accountService;
    }

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputUsername = request.getParameter("username");
        String inputPassword = request.getParameter("password");

        try {
            long userId = accountService.login(request, inputUsername, inputPassword);
            request.getSession().setAttribute("userId", userId);
            pageVars.remove("errorMessage");
            response.sendRedirect("/timer");
        }
        catch (AccountServiceException e) {
            pageVars.put("errorMessage", "Invalid username/password. Try again.");
            response.getWriter().println(PageGenerator.getPage(page, pageVars));
        }
    }

}
