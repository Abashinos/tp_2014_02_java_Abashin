package resources;

public class GameServerData implements Resource {
    private Integer portNumber;

    public Integer getPort() {
        if (portNumber == null) {
            throw new RuntimeException("No port in resource");
        }
        return portNumber;
    }
}
