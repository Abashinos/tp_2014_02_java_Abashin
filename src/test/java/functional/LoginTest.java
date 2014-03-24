package functional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginTest extends AuthTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void loginTest() throws Exception {
        String address = "http://localhost:" + PORT_NUMBER;

        accountService.signup(TEST_USERNAME, TEST_PASSWORD);
        Assert.assertTrue(executeAuthTest(address, TEST_USERNAME, TEST_PASSWORD));
        accountService.delete(TEST_USERNAME);
    }
}
