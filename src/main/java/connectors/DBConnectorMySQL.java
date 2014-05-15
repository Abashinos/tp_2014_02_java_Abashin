package connectors;

public final class DBConnectorMySQL implements IConnector {

    public final String getConfigurationFile() {
        return "mySqlDb.xml";
    }
}
