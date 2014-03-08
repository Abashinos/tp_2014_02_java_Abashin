package DAO;

import dataSets.UserDataSet;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import javax.jws.soap.SOAPBinding;

public class UserDAOimpl implements UserDAO {

    private SessionFactory sessionFactory;

    public UserDAOimpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public boolean add (UserDataSet dataSet) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(dataSet);
            transaction.commit();
        }
        catch (HibernateException e){
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

    public UserDataSet getByName (String name) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(UserDataSet.class);
        UserDataSet user = (UserDataSet) criteria.add(Restrictions.eq("username", name)).uniqueResult();
        session.close();

        return user;
    }
}
