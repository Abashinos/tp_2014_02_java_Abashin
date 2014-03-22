package services;

import connectors.DBConnectorH2;
import exceptions.DBException;
import exceptions.InvalidDataException;
import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static supplies.RandomSupply.randomStringGenerator;

public class AccountServiceTest {

    private static DBConnectorH2 dbConnector = new DBConnectorH2();
    private static UserDAOimpl userDAO = new UserDAOimpl(dbConnector.getSessionFactory());
    private static AccountService accountService = new AccountService(userDAO);
    private static final HttpServletRequest request = mock(HttpServletRequest.class);
    private static final HttpServletResponse response = mock(HttpServletResponse.class);
    private static final HttpSession session = mock(HttpSession.class);

    private static String TEST_USERNAME ;
    private static String TEST_PASSWORD ;
    private static boolean WAS_CAUGHT = false ;

    @Before
    public void setUp() {

    }
    @After
    public void tearDown() {

    }

    public static long registerUser(String username, String password) throws Exception {
        //HttpServletRequest tempRequest = mock(HttpServletRequest.class);
        //HttpSession tempSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        return accountService.signup(request, username, password);
    }
    public static long registerUser() throws Exception {
        when(request.getSession()).thenReturn(session);
        TEST_USERNAME = randomStringGenerator(10);
        TEST_PASSWORD = randomStringGenerator(10);
        return accountService.signup(request, TEST_USERNAME, TEST_PASSWORD);
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

        registerUser();
        //TODO: catch exception
        //Assert.assertTrue(accountService.login(request, TEST_USERNAME, TEST_PASSWORD));
        deleteUser();
    }
    @Test (expected = InvalidDataException.class)
    public void loginTestBad() throws Exception {
        String badUsername = randomStringGenerator(10);
        String badPassword = randomStringGenerator(10);
        accountService.login(request, badUsername, badPassword);
    }

    @Test
    public void registerTestGood() throws Exception {
        when(request.getSession()).thenReturn(session);

        TEST_USERNAME = randomStringGenerator(10);
        TEST_PASSWORD = randomStringGenerator(10);
        //TODO: catch exception
        //Assert.assertTrue(accountService.signup(request, TEST_USERNAME, TEST_PASSWORD));
        accountService.delete(TEST_USERNAME);
    }

    @Test
    public void registerTestBad() throws Exception {
        when(request.getSession()).thenReturn(session);

        registerUser();
        try {
            accountService.signup(request, TEST_USERNAME, TEST_PASSWORD);
        }
        catch (DBException e) {
            caughtException();
        }
        Assert.assertTrue(WAS_CAUGHT);
        deleteUser();
    }

}

