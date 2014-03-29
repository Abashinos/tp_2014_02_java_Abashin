package functional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
