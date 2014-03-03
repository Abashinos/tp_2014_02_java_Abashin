package DAO;

import java.sql.SQLException;

public interface UserDAO<T> {

    T getById (long id) throws SQLException;
    T getByName (String name) throws SQLException;
    boolean add (T dataSet) throws SQLException;
    // boolean delete (long id) throws SQLException;
}
