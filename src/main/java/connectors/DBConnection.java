package connectors;

import resources.resource_system.VFS;
import services.dataSets.UserDataSet;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.io.FileNotFoundException;

public final class DBConnection {

    protected final org.hibernate.cfg.Configuration conf = new org.hibernate.cfg.Configuration();
    protected SessionFactory sessionFactory;

    public DBConnection(IConnector connector) {
        conf.configure(connector.getConfigurationFile());
        setDataSets();
        setSessionBuilderConfiguration();
    }

    public final SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    protected final void setDataSets() {
        conf.addAnnotatedClass(UserDataSet.class);
    }

    protected final void setSessionBuilderConfiguration() {
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
        registryBuilder.applySettings(conf.getProperties());

        ServiceRegistry serviceRegistry = registryBuilder.build();
        this.sessionFactory = conf.buildSessionFactory(serviceRegistry);
    }
}
