package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class Redirector {

    public static void redirect (HttpServletRequest request, HttpServletResponse response, Map<String, Object> pageVars)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("/login");
        } else {
            pageVars.put("userId", userId);
        }
    }
}
