package resources;

public class GameServerData implements Resource {
    private Integer portNumber = null;

    public Integer getPort() {
        if (portNumber == null) {
            throw new RuntimeException("No port in resource");
        }
        return portNumber;
    }
}
