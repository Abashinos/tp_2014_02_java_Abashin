package connectors;

import dataSets.UserDataSet;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.hibernate.service.ServiceRegistry;

public class DBConnector {

    private org.hibernate.cfg.Configuration conf = new org.hibernate.cfg.Configuration();
    private SessionFactory sessionFactory;

    public DBConnector() {
        setConfigurationProperties();
        setDataSets();
        setSessionBuilderConfiguration();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private void setConfigurationProperties() {
        conf.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        conf.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        conf.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/javadb");
        conf.setProperty("hibernate.connection.username", "snake");
        conf.setProperty("hibernate.connection.password", "snake");
        conf.setProperty("hibernate.show_sql", "true");
        conf.setProperty("hibernate.hbm2ddl.auto", "validate");
    }

    private void setDataSets() {
        conf.addAnnotatedClass(UserDataSet.class);
    }

    private void setSessionBuilderConfiguration() {
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
        registryBuilder.applySettings(conf.getProperties());

        ServiceRegistry serviceRegistry = registryBuilder.build();
        this.sessionFactory = conf.buildSessionFactory(serviceRegistry);
    }
}
