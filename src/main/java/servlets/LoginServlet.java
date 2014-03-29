package servlets;

import exceptions.AccountServiceException;
import services.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static supplies.ResponseGenerator.*;

public class LoginServlet extends AuthServlet {

    public LoginServlet(AccountService accountService) {
        setPage("login.html");
        this.accountService = accountService;
    }

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputUsername = request.getParameter("username");
        String inputPassword = request.getParameter("password");

        try {
            long userId = accountService.login(inputUsername, inputPassword);
            request.getSession().setAttribute("userId", userId);
            removeFromPageVars("errorMessage");
            setRedirectData(response);
            response.sendRedirect("/timer");
        }
        catch (AccountServiceException e) {
            putInPageVars("errorMessage", "Invalid username/password. Try again.");
            setSuccessData(response);
            response.getWriter().println(PageGenerator.getPage(getPage(), getPageVars()));
        }
    }

}
