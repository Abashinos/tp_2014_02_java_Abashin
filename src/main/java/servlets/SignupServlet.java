package servlets;

import exceptions.DBException;
import generator.PageGenerator;
import org.apache.http.auth.InvalidCredentialsException;
import services.UserAccount;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignupServlet extends HttpServlet {

    private UserAccount userAccount;

    public SignupServlet (UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> pageVars = new HashMap<>();

        Redirector.redirect(request, response, pageVars);
    }

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String inputUsername = request.getParameter("username");
        String inputPassword = request.getParameter("password");
        String page = "login.html";
        Map <String, Object> pageVars = new HashMap<>();

        try {
            switch (request.getRequestURI()) {
                case ("/login"):
                    if (!inputUsername.equals("") && !inputPassword.equals("") && userAccount.login(request, inputUsername, inputPassword)) {
                        response.sendRedirect("/timer");
                    }
                    else {
                        page = "login.html";
                        pageVars.put("errorMessage", "Invalid username and/or password. Try again.");
                        response.getWriter().println(PageGenerator.getPage(page, pageVars));
                    }
                    break;
                case ("/signup"):
                    if (!inputUsername.equals("") && !inputPassword.equals("") && userAccount.signup(request, inputUsername, inputPassword)) {
                        response.sendRedirect("/timer");
                    }
                    else {
                        page = "signup.html";
                        pageVars.put("errorMessage", "Error during registration.");
                        response.getWriter().println(PageGenerator.getPage(page, pageVars));
                    }
                    break;
            }
        }
        catch (DBException e) {
            pageVars.put("errorMessage", e.getMessage());
            response.getWriter().println(PageGenerator.getPage(page, pageVars));
        }
    }
}
