package resource_system;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import resources.TestData;
import resources.resource_system.ResourceFactory;

public class ResourceSystemTest {

    private TestData testData = null;

    @Before
    public void setUp() {
        testData = (TestData) ResourceFactory.getInstance().get("test");
    }

    @Test
    public void integerTest() {
        Integer testPort = 8090;
        Assert.assertEquals(testPort, testData.portNumber);
    }

    @Test
    public void stringTest() {
        String testString = "test";
        Assert.assertEquals(testString, testData.testString);
    }
}
