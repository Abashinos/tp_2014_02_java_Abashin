package servlets;

import messaging.Address;
import messaging.MessageService;

public abstract class AbstractMServlet extends AbstractServlet implements ISubscriber, Runnable {
    private Address address;
    private MessageService messageService;

    @Override
    public void run() {
        messageService.addService(this);
        setSubscriber();
        try {
            while (true) {
                this.getMessageService().execForSubscriber(this);
                Thread.sleep(100);
            }
        } catch (InterruptedException ignored) {
            //TODO: remove service and subscriber
        }
    }

    @Override
    public Address getAddress() {
        return this.address;
    }

    @Override
    public MessageService getMessageService() {
        return this.messageService;
    }

    @Override
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
        this.address = new Address();
    }

    protected abstract void setSubscriber();
}
