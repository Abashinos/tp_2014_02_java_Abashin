package messaging;

import servlets.AbstractMServlet;
import servlets.ISubscriber;

public abstract class MessageToAMS extends Message {

    public MessageToAMS(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(ISubscriber subscriber) {
        exec((AbstractMServlet) subscriber);
    }

    public abstract void exec(AbstractMServlet baseServlet);
}