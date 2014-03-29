package services;

import services.dataSets.UserDataSet;
import exceptions.AccountServiceException;
import exceptions.InvalidDataException;

public class AccountService {

    private final UserDAO DAO;

    public AccountService(UserDAO dao) {
        this.DAO = dao;
    }

    public final long login (String inputUsername, String inputPassword) throws AccountServiceException {
        UserDataSet user = DAO.getByNameAndPassword(inputUsername, inputPassword);
        return user.getId();
    }

    public final long signup (String inputUsername, String inputPassword) throws AccountServiceException {
        if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
            throw new InvalidDataException("Username/password can't be empty.");
        }
        DAO.add(new UserDataSet(inputUsername, inputPassword));
        return login(inputUsername, inputPassword);
    }

    public final boolean delete (String username) throws InvalidDataException {
        return DAO.delete(username);
    }
}
