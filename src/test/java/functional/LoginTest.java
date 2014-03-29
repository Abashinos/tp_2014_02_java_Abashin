package functional;

import org.junit.*;

public class LoginTest extends AuthTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        accountService.signup(generatedTestUsername, generatedTestPassword);
    }

    @Test
    public void loginTest() throws Exception {
        Assert.assertTrue(executeAuthTest(baseAddress, generatedTestUsername, generatedTestPassword));
    }

    @After
    public void tearDown() throws Exception {
        accountService.delete(generatedTestUsername);
    }

}
