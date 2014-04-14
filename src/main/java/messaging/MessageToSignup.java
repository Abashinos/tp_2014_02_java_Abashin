package messaging;

import services.AccountService;
import services.UserSession;

public class MessageToSignup extends MessageToAccountService {
    private String sessionId;
    private String username;
    private String password;

    public MessageToSignup(String sessionId, Address from, Address to, String username, String password) {
        super(from, to);
        this.sessionId = sessionId;
        this.username = username;
        this.password = password;
    }

    public void exec(AccountService accountService){
        UserSession session = accountService.signup(username, password, sessionId);
        accountService.getMessageService().sendMessage(new MessageToAMSSS(this.getTo(), this.getFrom(), session));
    }
}

