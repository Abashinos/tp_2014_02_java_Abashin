package servlets;

import services.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static supplies.ResponseGenerator.setSuccessData;

public abstract class AuthServlet extends AbstractMServlet {

    protected AccountService accountService;

    @Override
    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = (Long) request.getSession().getAttribute("userId");

        if (userId != null) {
            putInPageVars("userId", userId);
        }

        removeFromPageVars("errorMessage");
        setSuccessData(response);
        response.getWriter().println(PageGenerator.getPage(getPage(), getPageVars()));
    }
}
