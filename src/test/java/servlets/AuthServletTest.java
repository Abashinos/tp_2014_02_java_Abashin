package servlets;

import connectors.DBConnector;
import connectors.DBConnectorH2;
import services.AccountService;
import services.UserDAOimpl;

import static org.mockito.Mockito.when;
import static supplies.RandomSupply.randomStringGenerator;

public class AuthServletTest extends AbstractServletTest {

    protected static String TEST_USERNAME;
    protected static String TEST_PASSWORD;
    protected static AccountService accountService;

    protected void setUp() throws Exception {
        DBConnector dbConnector = new DBConnectorH2();
        UserDAOimpl userDAO = new UserDAOimpl(dbConnector.getSessionFactory());
        accountService = new AccountService(userDAO);

        TEST_USERNAME = randomStringGenerator(10);
        TEST_PASSWORD = randomStringGenerator(10);
        when(request.getParameter("username")).thenReturn(TEST_USERNAME);
        when(request.getParameter("password")).thenReturn(TEST_PASSWORD);

        super.setUp();
    }
}
