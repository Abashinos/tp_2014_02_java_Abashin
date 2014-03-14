package connectors;

import dataSets.UserDataSet;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public abstract class DBConnector {

    protected org.hibernate.cfg.Configuration conf = new org.hibernate.cfg.Configuration();
    protected SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    protected void setDataSets() {
        conf.addAnnotatedClass(UserDataSet.class);
    }

    protected void setSessionBuilderConfiguration() {
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
        registryBuilder.applySettings(conf.getProperties());

        ServiceRegistry serviceRegistry = registryBuilder.build();
        this.sessionFactory = conf.buildSessionFactory(serviceRegistry);
    }
}
