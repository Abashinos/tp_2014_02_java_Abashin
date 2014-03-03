package servlets;

import DAO.UserDAOimpl;
import dataSets.UserDataSet;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicLong;

public class UserAccount {

    private AtomicLong userIdGenerator = new AtomicLong();
    private UserDAOimpl DAO;

    public UserAccount (UserDAOimpl dao) {
        this.DAO = dao;
    }

    private boolean userExists(String username, String password) {
        UserDataSet user = DAO.getByName(username);
        return user != null && user.getPassword().equals(password);
    }

    public boolean login (HttpServletRequest request, String inputUsername, String inputPassword) {
        if (userExists(inputUsername, inputPassword)) {
            long userId = userIdGenerator.getAndIncrement();
            request.getSession().setAttribute("userId", userId);
            return true;
        }
        else
            return false;
    }

    public boolean signup (HttpServletRequest request, String inputUsername, String inputPassword) {
        boolean isRegistered = DAO.add(new UserDataSet(inputUsername, inputPassword));
        return isRegistered && login(request, inputUsername, inputPassword);
    }


}
