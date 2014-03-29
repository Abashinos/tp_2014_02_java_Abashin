package services;

import services.dataSets.UserDataSet;
import exceptions.DBException;
import exceptions.InvalidDataException;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;


public class UserDAOimpl implements UserDAO {

    private SessionFactory sessionFactory;

    public UserDAOimpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void add (UserDataSet dataSet) throws DBException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(dataSet);
            transaction.commit();
        }
        catch (ConstraintViolationException e) {
            throw new DBException("User already exists.");
        }
        catch (HibernateException e){
            session.close();
        }
        session.close();
    }

    public boolean delete (String username) throws InvalidDataException {
        Session session = sessionFactory.openSession();

        UserDataSet user = getByName(username);
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(user);
            transaction.commit();
        }
        catch (HibernateException e) {
            session.close();
            return false;
        }

        session.close();
        return true;
    }

    public UserDataSet getById (long id) {
        Session session = sessionFactory.openSession();
        UserDataSet user = (UserDataSet) session.load(UserDataSet.class, id);
        session.close();

        return user;
    }

    public UserDataSet getByName (String name) throws InvalidDataException {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(UserDataSet.class);
        UserDataSet user = (UserDataSet) criteria.add(Restrictions.eq("username", name)).uniqueResult();
        session.close();

        if (user == null)
            throw new InvalidDataException();

        return user;
    }
}
