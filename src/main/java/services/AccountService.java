package services;

import DAO.UserDAOimpl;
import dataSets.UserDataSet;
import exceptions.DBException;
import exceptions.InvalidDataException;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicLong;

public class AccountService {

    private UserDAOimpl DAO;

    public AccountService(UserDAOimpl dao) {
        this.DAO = dao;
    }

    public boolean login (HttpServletRequest request, String inputUsername, String inputPassword) throws DBException {
        UserDataSet user = DAO.getByName(inputUsername);
        if (user != null && user.getPassword().equals(inputPassword)) {
            long userId = user.getId();
            request.getSession().setAttribute("userId", userId);
            return true;
        }
        else
            return false;
    }

    public boolean signup (HttpServletRequest request, String inputUsername, String inputPassword) throws DBException {
        boolean isRegistered = DAO.add(new UserDataSet(inputUsername, inputPassword));
        return isRegistered && login(request, inputUsername, inputPassword);
    }

    public boolean delete (String username) throws InvalidDataException {
        return DAO.delete(username);
    }
}
