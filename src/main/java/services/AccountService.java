package services;

import services.dataSets.UserDataSet;
import exceptions.AccountServiceException;
import exceptions.InvalidDataException;

public class AccountService {

    private UserDAO DAO;

    public AccountService(UserDAO dao) {
        this.DAO = dao;
    }

    public long login (String inputUsername, String inputPassword) throws AccountServiceException {
        UserDataSet user = DAO.getByName(inputUsername);
        if (user.getPassword().equals(inputPassword)) {
            return user.getId();
        }
        else {
            throw new AccountServiceException();
        }
    }

    public long signup (String inputUsername, String inputPassword) throws AccountServiceException {
        DAO.add(new UserDataSet(inputUsername, inputPassword));
        return login(inputUsername, inputPassword);
    }

    public boolean delete (String username) throws InvalidDataException {
        return DAO.delete(username);
    }
}
