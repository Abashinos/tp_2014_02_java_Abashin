package functional;

import org.junit.*;

public class SignupTest extends AuthTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void signupTest() throws Exception {
        Assert.assertTrue(executeAuthTest(baseAddress + "/signup", generatedTestUsername, generatedTestPassword));
    }

    @After
    public void tearDown() throws Exception {
        accountService.delete(generatedTestUsername);
    }

}
