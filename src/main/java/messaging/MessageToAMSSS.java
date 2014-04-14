package messaging;

import services.UserSession;
import servlets.AbstractMServlet;

public class MessageToAMSSS extends MessageToAMS {

    private UserSession session;

    public MessageToAMSSS(Address from, Address to, UserSession session) {
        super(from, to);
        this.session = session;
    }

    @Override
    public void exec(AbstractMServlet servlet) {
        servlet.setSession(session);
    }
}
