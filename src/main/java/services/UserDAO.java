package services;

import services.dataSets.UserDataSet;
import exceptions.DBException;
import exceptions.InvalidDataException;


public interface UserDAO {

    UserDataSet getById (long id);
    UserDataSet getByName (String name) throws InvalidDataException;
    UserDataSet getByNameAndPassword (String name, String password) throws InvalidDataException;
    void add (UserDataSet dataSet) throws DBException;
    boolean delete (String name) throws InvalidDataException;
}
