package functional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SignupTest extends AuthTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void signupTest() throws Exception {
        String address = "http://localhost:" + PORT_NUMBER + "/signup";

        Assert.assertTrue(executeAuthTest(address, TEST_USERNAME, TEST_PASSWORD));
        accountService.delete(TEST_USERNAME);
    }

}
