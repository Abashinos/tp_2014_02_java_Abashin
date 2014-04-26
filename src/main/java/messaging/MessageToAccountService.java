package messaging;

import services.AccountService;
import servlets.ISubscriber;

public abstract class MessageToAccountService extends Message {

    public MessageToAccountService(Address from, Address to) {
        super(from, to);
    }

    public void exec(ISubscriber subscriber) {
        exec((AccountService) subscriber);
    }

    public abstract void exec(AccountService accountService);

}
