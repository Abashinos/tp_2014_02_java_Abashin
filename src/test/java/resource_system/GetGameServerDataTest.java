package resource_system;

import org.junit.Before;
import org.junit.Test;
import resources.GameServerData;
import resources.resource_system.ResourceFactory;

public class GetGameServerDataTest {

    private GameServerData gameServerData = null;

    @Before
    public void setUp() {

    }

    //TODO: add test on "test.xml"
    @Test
    public void getGameServerDataTestGood() {
        gameServerData = (GameServerData) ResourceFactory.getInstance().get("gameServerData");
        gameServerData.getPort();
    }

    @Test(expected = RuntimeException.class)
    public void getGameServerDataTestBad() {
        gameServerData = new GameServerData();
        gameServerData.getPort();
    }
}
