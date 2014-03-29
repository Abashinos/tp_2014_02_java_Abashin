package servlets;

import connectors.DBConnection;
import connectors.DBConnectorH2;
import services.AccountService;
import services.UserDAOimpl;

import static org.mockito.Mockito.when;
import static supplies.RandomSupply.randomStringGenerator;

public class AuthServletTest extends AbstractServletTest {

    protected static String generatedTestUsername;
    protected static String generatedTestPassword;
    protected static AccountService accountService;

    protected void setUp() throws Exception {
        super.setUp();

        DBConnection dbConnection = new DBConnection ( new DBConnectorH2() );
        UserDAOimpl userDAO = new UserDAOimpl(dbConnection.getSessionFactory());
        accountService = new AccountService(userDAO);

        generatedTestUsername = randomStringGenerator(10);
        generatedTestPassword = randomStringGenerator(10);
        when(request.getParameter("username")).thenReturn(generatedTestUsername);
        when(request.getParameter("password")).thenReturn(generatedTestPassword);


    }
}
