package services;

import DAO.UserDAOimpl;
import dataSets.UserDataSet;
import exceptions.DBException;
import exceptions.InvalidDataException;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicLong;

public class UserAccount {

    private AtomicLong userIdGenerator = new AtomicLong();
    private UserDAOimpl DAO;

    public UserAccount (UserDAOimpl dao) {
        this.DAO = dao;
    }

    private boolean userExists(String username, String password) throws DBException {
        UserDataSet user = DAO.getByName(username);
        if (user == null || !user.getPassword().equals(password))
            throw new InvalidDataException();
        else
            return true;
    }

    public boolean login (HttpServletRequest request, String inputUsername, String inputPassword) throws DBException {
        if (userExists(inputUsername, inputPassword)) {
            long userId = userIdGenerator.getAndIncrement();
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
