package DAO;

import dataSets.UserDataSet;

import java.sql.SQLException;

public interface UserDAO {

    UserDataSet getById (long id) throws SQLException;
    UserDataSet getByName (String name) throws SQLException;
    boolean add (UserDataSet dataSet) throws SQLException;
    // boolean delete (long id) throws SQLException;
}
