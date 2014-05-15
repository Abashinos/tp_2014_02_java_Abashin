package connectors;

public final class DBConnectorH2 implements IConnector {

    public final String getConfigurationFile() {
        return "h2Db.xml";
    }
}
