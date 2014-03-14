package services;

import DAO.UserDAOimpl;
import connectors.DBConnectorH2;
import exceptions.DBException;
import exceptions.InvalidDataException;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static supplies.RandomSupply.randomStringGenerator;

public class UserAccountTest {

    private static DBConnectorH2 dbConnector = new DBConnectorH2();
    private static UserDAOimpl userDAO = new UserDAOimpl(dbConnector.getSessionFactory());
    private static UserAccount userAccount = new UserAccount(userDAO);
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

    public static boolean registerUser(String username, String password) throws Exception {
        //HttpServletRequest tempRequest = mock(HttpServletRequest.class);
        //HttpSession tempSession = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        return userAccount.signup(request, username, password);
    }
    public static boolean registerUser() throws Exception {
        when(request.getSession()).thenReturn(session);
        TEST_USERNAME = randomStringGenerator(10);
        TEST_PASSWORD = randomStringGenerator(10);
        return userAccount.signup(request, TEST_USERNAME, TEST_PASSWORD);
    }

    public static boolean deleteUser(String username) throws InvalidDataException {
        return userAccount.delete(username);
    }
    public static boolean deleteUser() throws InvalidDataException {
        return userAccount.delete(TEST_USERNAME);
    }

    public static void caughtException() {
        WAS_CAUGHT = true;
    }

    @Test
    public void loginTestGood() throws Exception {

        registerUser();
        Assert.assertTrue(userAccount.login(request, TEST_USERNAME, TEST_PASSWORD));
        deleteUser();
    }
    @Test (expected = InvalidDataException.class)
    public void loginTestBad() throws Exception {
        String badUsername = randomStringGenerator(10);
        String badPassword = randomStringGenerator(10);
        userAccount.login(request, badUsername, badPassword);
    }

    @Test
    public void registerTestGood() throws Exception {
        when(request.getSession()).thenReturn(session);

        TEST_USERNAME = randomStringGenerator(10);
        TEST_PASSWORD = randomStringGenerator(10);
        Assert.assertTrue(userAccount.signup(request, TEST_USERNAME, TEST_PASSWORD));
        userAccount.delete(TEST_USERNAME);
    }

    @Test
    public void registerTestBad() throws Exception {
        when(request.getSession()).thenReturn(session);

        registerUser();
        try {
            userAccount.signup(request, TEST_USERNAME, TEST_PASSWORD);
        }
        catch (DBException e) {
            caughtException();
        }
        Assert.assertTrue(WAS_CAUGHT);
        deleteUser();
    }

}

