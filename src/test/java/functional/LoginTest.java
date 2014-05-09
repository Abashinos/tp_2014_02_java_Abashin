package functional;

import org.junit.*;

public class LoginTest extends AuthTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void loginTestGood() throws Exception {
        accountService.signup(generatedTestUsername, generatedTestPassword, generatedSessionId);
        Assert.assertTrue(executeAuthTest(address, generatedTestUsername, generatedTestPassword));
        accountService.delete(generatedTestUsername);
    }

    @Test
    public void loginTestBad() throws Exception {
        Assert.assertFalse(executeAuthTest(address, generatedTestUsername, generatedTestPassword));
    }

    @After
    public void tearDown() throws Exception {

    }

}
