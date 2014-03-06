package servlets;

import DAO.UserDAOimpl;
import generator.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignupServlet extends HttpServlet {

    private UserAccount userAccount;

    public SignupServlet (UserDAOimpl dao) {
        this.userAccount = new UserAccount (dao);
    }

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String page = "login.html";
        Map<String, Object> pageVars = new HashMap<>();

        Redirector.redirect(request, response, page, pageVars);
    }

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String inputUsername = request.getParameter("username");
        String inputPassword = request.getParameter("password");
        String page;
        Map <String, Object> pageVars = new HashMap<>();

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
}
