package connectors;

import org.hibernate.cfg.Configuration;

public class DBConnectorH2 implements IConnector {

    public final void setConfigurationProperties(Configuration conf) {
        conf.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        conf.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        conf.setProperty("hibernate.connection.url", "jdbc:h2:mem:java_db;INIT=RUNSCRIPT FROM 'src/create.sql';MVCC=true");
        conf.setProperty("hibernate.connection.username", "snake");
        conf.setProperty("hibernate.connection.password", "snake");
        conf.setProperty("hibernate.show_sql", "true");
        conf.setProperty("hibernate.hbm2ddl.auto", "validate");
    }
}
