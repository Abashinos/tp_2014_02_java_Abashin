package servlets;

import exceptions.AccountServiceException;
import services.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignupServlet extends AuthServlet {

    private final Map<String, Object> pageVars = new HashMap<>();

    public SignupServlet (AccountService accountService) {
        this.page = "signup.html";
        this.accountService = accountService;
    }


    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputUsername = request.getParameter("username");
        String inputPassword = request.getParameter("password");

        try {
            long userId = accountService.signup(inputUsername, inputPassword);
            request.getSession().setAttribute("userId", userId);
            pageVars.remove("errorMessage");
            response.sendRedirect("/timer");
        }
        catch (AccountServiceException e) {
            pageVars.put("errorMessage", "Error during registration.");
            response.getWriter().println(PageGenerator.getPage(page, pageVars));
        }

    }
}
