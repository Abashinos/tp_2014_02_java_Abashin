package messaging;

import exceptions.AccountServiceException;
import services.AccountService;
import services.UserSession;
import servlets.ISubscriber;

public class MessageToLogin extends MessageToAccountService {
    private String sessionId;
    private String username;
    private String password;

    public MessageToLogin(String sessionId, Address from, Address to, String username, String password) {
        super(from, to);
        this.sessionId = sessionId;
        this.username = username;
        this.password = password;
    }

    public void exec(AccountService accountService){
        UserSession session = accountService.login(username, password, sessionId);


    }
}
