package services;

import DAO.UserDAOimpl;
import connectors.DBConnector;
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

    private static UserAccount userAccount;
    private static final HttpServletRequest request = mock(HttpServletRequest.class);
    private static final HttpServletResponse response = mock(HttpServletResponse.class);
    private static final HttpSession session = mock(HttpSession.class);

    private static String TEST_USERNAME ;
    private static String TEST_PASSWORD ;

    @Before
    public void setUp() {
        DBConnector dbConnector = new DBConnector("H2");
        UserDAOimpl userDAO = new UserDAOimpl(dbConnector.getSessionFactory());
        userAccount = new UserAccount(userDAO);
    }
    @After
    public void tearDown() {

    }

    public boolean registerUser() throws Exception {
        when(request.getSession()).thenReturn(session);
        TEST_USERNAME = randomStringGenerator(10);
        TEST_PASSWORD = randomStringGenerator(10);
        return userAccount.signup(request, TEST_USERNAME, TEST_PASSWORD);
    }

    public static boolean deleteUser() throws InvalidDataException {
        return userAccount.delete(TEST_USERNAME);
    }

    @Test
    public void loginTestGood() throws Exception {
        when(request.getSession()).thenReturn(session);
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

}

