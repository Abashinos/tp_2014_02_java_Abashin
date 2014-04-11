package services;

import messaging.Address;
import messaging.MessageService;
import services.dataSets.UserDataSet;
import exceptions.AccountServiceException;
import exceptions.InvalidDataException;
import servlets.ISubscriber;

public class AccountService implements ISubscriber, Runnable {

    private final UserDAO DAO;
    private MessageService messageService;
    private Address address;

    public AccountService(UserDAO dao, MessageService messageService){
        this.DAO = dao;
        this.messageService = messageService;
    }

    @Override
    public void run() {
        getMessageService().addService(this);
        getMessageService().getAddressService().setAccountServiceAddress(getAddress());
        try {
            while (true) {
                this.getMessageService().execForSubsciber(this);
                Thread.sleep(100);
            }
        } catch (InterruptedException ignored) {

        }
    }

    public final UserSession login (String inputUsername, String inputPassword, final String sessionId) {
        try {
            UserDataSet user = DAO.getByNameAndPassword(inputUsername, inputPassword);
            if (user.getPassword().equals(inputPassword)) {
                return new UserSession(sessionId, user.getId(), inputUsername, UserSession.Status.OK);
            }
            else {
                throw new InvalidDataException();
            }
        }
        catch (InvalidDataException e) {
            return UserSession.getUserSessionError(sessionId, inputUsername, e.getMessage());
        }
    }

    public final UserSession signup (String inputUsername, String inputPassword, final String sessionId) {
        if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
            throw new InvalidDataException("Username/password can't be empty.");
        }
        DAO.add(new UserDataSet(inputUsername, inputPassword));
        return login(inputUsername, inputPassword, sessionId);
    }

    public final boolean delete (String username) throws InvalidDataException {
        return DAO.delete(username);
    }


    public MessageService getMessageService() {
        return messageService;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
        this.address = new Address();
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
