package services;

import dataSets.UserDataSet;
import exceptions.AccountServiceException;
import exceptions.DBException;
import exceptions.InvalidDataException;

import javax.servlet.http.HttpServletRequest;

public class AccountService {

    private UserDAOimpl DAO;

    public AccountService(UserDAOimpl dao) {
        this.DAO = dao;
    }

    public long login (HttpServletRequest request, String inputUsername, String inputPassword) throws AccountServiceException {
        UserDataSet user = DAO.getByName(inputUsername);
        if (user.getPassword().equals(inputPassword)) {
            long userId = user.getId();
            return userId;
        }
        else {
            throw new AccountServiceException();
        }
    }

    public long signup (HttpServletRequest request, String inputUsername, String inputPassword) throws AccountServiceException {
        DAO.add(new UserDataSet(inputUsername, inputPassword));
        return login(request, inputUsername, inputPassword);
    }

    public boolean delete (String username) throws InvalidDataException {
        return DAO.delete(username);
    }
}
