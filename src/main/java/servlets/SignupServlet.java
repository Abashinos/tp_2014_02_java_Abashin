package servlets;

import messaging.Address;
import messaging.Message;
import messaging.MessageToSignup;
import services.UserSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static supplies.ResponseGenerator.*;

public class SignupServlet extends AuthServlet {

    public SignupServlet () {
        setPage(pagesData.SIGNUP_PAGE);
    }

    @Override
    public void setSubscriber() {
        this.getMessageService().getAddressService().setSignupServletAddress(this.getAddress());
    }
    public void removeSubscriber() {
        this.getMessageService().getAddressService().setSignupServletAddress(null);
    }

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputUsername = request.getParameter("username");
        String inputPassword = request.getParameter("password");
        String sessionId = request.getSession().getId();
        Address accountServiceAddress = this.getMessageService().getAddressService().getAccountServiceAddress();

        if (accountServiceAddress != null) {
            this.getSessionMap().put(sessionId, new UserSession(sessionId));

            Message signupMessage = new MessageToSignup(sessionId, this.getAddress(), accountServiceAddress, inputUsername, inputPassword);
            this.getMessageService().sendMessage(signupMessage);

            response.sendRedirect("/wait");
        }
        else {
            setSuccessData(response);
            this.getSessionMap().put(sessionId, UserSession.getUserSessionError(sessionId, inputUsername, "DB error"));
        }

    }


}
