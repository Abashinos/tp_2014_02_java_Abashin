package functional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import services.AccountServiceTest;

import static supplies.RandomSupply.randomStringGenerator;

public class SignupTest extends AuthTest{

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void signupTest() throws Exception {
        String username = randomStringGenerator(10);
        String password = randomStringGenerator(10);
        String address = "http://localhost:" + PORT_NUMBER + "/signup";

        Assert.assertTrue(executeAuthTest(address, username, password));
        AccountServiceTest.deleteUser(username);
    }

}
