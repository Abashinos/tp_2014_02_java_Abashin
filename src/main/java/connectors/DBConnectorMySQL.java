package connectors;

public class DBConnectorMySQL extends DBConnector {

    public DBConnectorMySQL() {
        setConfigurationProperties();
        setDataSets();
        setSessionBuilderConfiguration();
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

}
