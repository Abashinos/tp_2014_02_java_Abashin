package functional;

import org.junit.*;

public class SignupTest extends AuthTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        address += "/signup";
    }

    @Test
    public void signupTestGood() throws Exception {
        Assert.assertTrue(executeAuthTest(address, generatedTestUsername, generatedTestPassword));
    }
    @Test
    public void signupTestBad() throws Exception {
        executeAuthTest(address, generatedTestUsername, generatedTestPassword);
        Assert.assertFalse(executeAuthTest(address, generatedTestUsername, generatedTestPassword));
    }

    @After
    public void tearDown() throws Exception {
        accountService.delete(generatedTestUsername);
    }

}
