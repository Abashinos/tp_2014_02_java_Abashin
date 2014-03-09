package servlets;

import DAO.UserDAOimpl;
import connectors.DBConnector;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import services.UserAccount;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SignupServletTest {
    private static final HttpServletRequest request = mock(HttpServletRequest.class);
    private static final HttpServletResponse response = mock(HttpServletResponse.class);
    private static final HttpSession session = mock(HttpSession.class);
    private static SignupServlet signupServlet;
    private static final StringWriter stringWriter = new StringWriter();
    private static final PrintWriter printWriter = new PrintWriter(stringWriter);

    @Before
    public void setUp() throws Exception {
        DBConnector dbConnector = new DBConnector("H2");
        UserDAOimpl userDAO = new UserDAOimpl(dbConnector.getSessionFactory());
        UserAccount userAccount = new UserAccount(userDAO);
        signupServlet = new SignupServlet(userAccount);

        when(request.getSession()).thenReturn(session);
        when(response.getWriter()).thenReturn(printWriter);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getLoginPageLoggedInTest() throws Exception {
        when(request.getRequestURI()).thenReturn("/login");
        when(request.getServletPath()).thenReturn("/login");
        when(session.getAttribute("userId")).thenReturn(0L);
        signupServlet.doGet(request, response);

        Assert.assertTrue(stringWriter.toString().contains("<meta name=\"page\" content=\"login\">"));
    }
    @Test
    public void getLoginPageNotLoggedInTest() throws Exception {
        when(request.getRequestURI()).thenReturn("/login");
        when(request.getServletPath()).thenReturn("/login");
        when(session.getAttribute("userId")).thenReturn(null);
        signupServlet.doGet(request, response);

        Assert.assertTrue(stringWriter.toString().contains("<meta name=\"page\" content=\"login\">"));
    }

    @Test
    public void getSignupPageLoggedInTest() throws Exception {
        when(request.getRequestURI()).thenReturn("/signup");
        when(request.getServletPath()).thenReturn("/signup");
        when(session.getAttribute("userId")).thenReturn(0L);
        signupServlet.doGet(request, response);

        Assert.assertTrue(stringWriter.toString().contains("<meta name=\"page\" content=\"signup\">"));
    }
    @Test
    public void getSignupPageNotLoggedInTest() throws Exception {
        when(request.getRequestURI()).thenReturn("/signup");
        when(request.getServletPath()).thenReturn("/signup");
        when(session.getAttribute("userId")).thenReturn(null);
        signupServlet.doGet(request, response);

        Assert.assertTrue(stringWriter.toString().contains("<meta name=\"page\" content=\"signup\">"));
    }
}
