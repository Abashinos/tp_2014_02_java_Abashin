package messaging;

import services.UserSession;
import servlets.AbstractMServlet;

public class MessageToServletSetSession extends MessageToAbstractMServlet {

    private UserSession session;

    public MessageToServletSetSession(Address from, Address to, UserSession session) {
        super(from, to);
        this.session = session;
    }

    @Override
    public void exec(AbstractMServlet servlet) {
        servlet.setSession(session);
    }
}
