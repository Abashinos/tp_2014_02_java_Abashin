package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Redirector {

    public static void redirect (HttpServletRequest request, HttpServletResponse response, Map<String, Object> pageVars)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        switch (request.getServletPath()) {

            case (""):
                if (userId == null) {
                    response.sendRedirect("/login");
                } else {
                    pageVars.put("userId", userId);
                }
                break;
            case ("/signup"):
            case ("/login"):
                if (userId != null) {
                    pageVars.put("userId", userId);
                }
                break;
            default:
                System.out.print("nope");
                break;
        }

    }
}