package services;

import connectors.DBConnector;
import connectors.DBConnectorH2;
import exceptions.AccountServiceException;
import exceptions.DBException;
import exceptions.InvalidDataException;
import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import servlets.AbstractServletTest;

import static org.mockito.Mockito.when;
import static supplies.RandomSupply.randomStringGenerator;

public class AccountServiceTest extends AbstractServletTest {

    private static AccountService accountService;
    private static String TEST_USERNAME;
    private static String TEST_PASSWORD;
    private static boolean WAS_CAUGHT;

    @Before
    public void setUp() {
        DBConnector dbConnector = new DBConnectorH2();
        UserDAOimpl userDAO = new UserDAOimpl(dbConnector.getSessionFactory());
        accountService = new AccountService(userDAO);

        TEST_USERNAME = randomStringGenerator(10);
        TEST_PASSWORD = randomStringGenerator(10);
        WAS_CAUGHT = false;
    }
    @After
    public void tearDown() {

    }

    public static long registerUser(String username, String password) throws Exception {
        return accountService.signup(username, password);
    }
    public static long registerUser() throws Exception {

        return accountService.signup(TEST_USERNAME, TEST_PASSWORD);
    }

    public static boolean deleteUser(String username) throws InvalidDataException {
        return accountService.delete(username);
    }
    public static boolean deleteUser() throws InvalidDataException {
        return accountService.delete(TEST_USERNAME);
    }

    public static void caughtException() {
        WAS_CAUGHT = true;
    }

    @Test
    public void loginTestGood() throws Exception {

        try {
            registerUser();
        }
        catch ( AccountServiceException e ) {
            WAS_CAUGHT = true;
        }
        Assert.assertFalse(WAS_CAUGHT);
        deleteUser();
    }

    @Test (expected = AccountServiceException.class)
    public void loginTestBad() throws Exception {
        accountService.login(TEST_USERNAME, TEST_PASSWORD);
    }

    @Test
    public void registerTestGood() throws Exception {

        try {
            registerUser();
        }
        catch ( AccountServiceException e ) {
            WAS_CAUGHT = true;
        }
        Assert.assertFalse(WAS_CAUGHT);
        deleteUser();
    }

    @Test
    public void registerTestBad() throws Exception {
        when(request.getSession()).thenReturn(session);

        registerUser();
        try {
            accountService.signup(TEST_USERNAME, TEST_PASSWORD);
        }
        catch (DBException e) {
            caughtException();
        }
        Assert.assertTrue(WAS_CAUGHT);
        deleteUser();
    }

}

