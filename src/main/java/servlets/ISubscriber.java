package servlets;

import messaging.Address;
import messaging.MessageService;

public interface ISubscriber {
    public Address getAddress();
    public MessageService getMessageService();
    public void setMessageService(MessageService messageService);
}
