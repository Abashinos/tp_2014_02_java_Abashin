package services;

import connectors.DBConnection;
import connectors.DBConnectorH2;
import exceptions.AccountServiceException;
import exceptions.DBException;
import exceptions.InvalidDataException;
import messaging.ServletFactory;
import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import servlets.AbstractServletTest;

import static org.mockito.Mockito.when;
import static supplies.RandomSupply.randomStringGenerator;

public class AccountServiceTest extends AbstractServletTest {

    private static AccountService accountService =
                                  ServletFactory.makeAccountService(new DBConnection(new DBConnectorH2()));

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }
    @After
    public void tearDown() {

    }

    public static boolean signup() throws Exception {
        UserSession userSession =
                    accountService.signup(generatedTestUsername, generatedTestPassword, generatedSessionID);
        return userSession != null &&
               userSession.getUsername().equals(generatedTestUsername) &&
               userSession.getSessionId().equals(generatedSessionID) &&
               userSession.getStatus() == UserSession.Status.OK;
    }
    public static boolean login() throws Exception {
        UserSession userSession =
                accountService.login(generatedTestUsername, generatedTestPassword, generatedSessionID);
        return userSession != null &&
               userSession.getUsername().equals(generatedTestUsername) &&
               userSession.getSessionId().equals(generatedSessionID) &&
               userSession.getStatus() == UserSession.Status.OK;
    }

    public static boolean deleteUser() throws InvalidDataException {
        return accountService.delete(generatedTestUsername);
    }


    @Test
    public void loginTestGood() throws Exception {
        Assert.assertTrue(signup());
        Assert.assertTrue(login());
        deleteUser();
    }

    @Test
    public void loginTestBad() throws Exception {
        Assert.assertFalse(login());
    }

    @Test
    public void signupTestGood() throws Exception {

        Assert.assertTrue(signup());
        deleteUser();
    }

    @Test
    public void registerTestBad() throws Exception {

        Assert.assertTrue(signup());
        Assert.assertFalse(signup());
        deleteUser();
    }

}

