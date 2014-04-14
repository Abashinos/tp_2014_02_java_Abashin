package servlets;

import exceptions.AccountServiceException;
import messaging.Address;
import messaging.Message;
import messaging.MessageToLogin;
import services.AccountService;
import services.UserSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static supplies.ResponseGenerator.*;

public class LoginServlet extends AuthServlet {

    public LoginServlet() {
        setPage("login.html");
    }

    @Override
    public void setSubscriber() {
        this.getMessageService().getAddressService().setLoginServletAddress(this.getAddress());
    }

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputUsername = request.getParameter("username");
        String inputPassword = request.getParameter("password");
        String sessionId = request.getSession().getId();
        Address accountServiceAddress = this.getMessageService().getAddressService().getAccountServiceAddress();

        if (accountServiceAddress != null) {
            this.getSessionMap().put(sessionId, new UserSession(sessionId));

            Message loginMessage = new MessageToLogin(sessionId, this.getAddress(), accountServiceAddress, inputUsername, inputPassword);
            this.getMessageService().sendMessage(loginMessage);

            response.sendRedirect("/wait");
        }
        else {
            setSuccessData(response);

            this.getSessionMap().put(sessionId, UserSession.getUserSessionError(sessionId, inputUsername, "DB error"));
        }
    }

}
