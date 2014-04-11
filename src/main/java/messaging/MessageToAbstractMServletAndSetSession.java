package messaging;

import services.UserSession;
import servlets.AbstractMServlet;

public class MessageToAbstractMServletAndSetSession extends MessageToAbstractMServlet{

    private UserSession session;

    public MessageToAbstractMServletAndSetSession(Address from, Address to, UserSession session) {
        super(from, to);
        this.session = session;
    }

    @Override
    public void exec(AbstractMServlet servlet) {
        servlet.setSessionMap(session);
    }
}
