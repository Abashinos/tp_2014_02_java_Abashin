package servlets;

import DAO.UserDAOimpl;
import connectors.DBConnectorH2;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import services.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;
import static supplies.RandomSupply.randomStringGenerator;

public class SignupServletTest {
    private static AccountService accountService;
    private static final HttpServletRequest request = mock(HttpServletRequest.class);
    private static final HttpServletResponse response = mock(HttpServletResponse.class);
    private static final HttpSession session = mock(HttpSession.class);
    private static SignupServlet signupServlet;
    private static final StringWriter stringWriter = new StringWriter();
    private static final PrintWriter printWriter = new PrintWriter(stringWriter);

    @Before
    public void setUp() throws Exception {
        DBConnectorH2 dbConnector = new DBConnectorH2();
        UserDAOimpl userDAO = new UserDAOimpl(dbConnector.getSessionFactory());
        accountService = new AccountService(userDAO);
        signupServlet = new SignupServlet(accountService);

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

    @Test
    public void doGetTest() throws Exception {
        when (request.getRequestURI()).thenReturn("/login");
        when(request.getServletPath()).thenReturn("/login");
        signupServlet.doGet(request, response);
        Assert.assertTrue(stringWriter.toString().contains("<meta name=\"page\" content=\"login\">"));
    }

    @Test
    public void doPostSignupGood() throws Exception {
        String username = randomStringGenerator(10);
        String password = randomStringGenerator(10);
        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getRequestURI()).thenReturn("/signup");
        signupServlet.doPost(request, response);

        verify(response, atLeastOnce()).sendRedirect("/timer");
        accountService.delete(username);
    }
    @Test
    public void doPostSignupBad() throws Exception {
        String username = randomStringGenerator(10);
        String password = randomStringGenerator(10);
        accountService.signup(request, username, password);

        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getRequestURI()).thenReturn("/signup");
        signupServlet.doPost(request, response);

        Assert.assertTrue(stringWriter.toString().contains("id=\"errormessage\""));
        accountService.delete(username);
    }

    @Test
    public void doPostLoginGood() throws Exception {
        String username = randomStringGenerator(10);
        String password = randomStringGenerator(10);
        accountService.signup(request, username, password);

        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getRequestURI()).thenReturn("/login");
        signupServlet.doPost(request, response);

        verify(response, atLeastOnce()).sendRedirect("/timer");
        accountService.delete(username);
    }

    @Test
    public void doPostLoginBad() throws Exception {
        String username = randomStringGenerator(10);
        String password = randomStringGenerator(10);

        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getRequestURI()).thenReturn("/login");
        signupServlet.doPost(request, response);

        Assert.assertTrue(stringWriter.toString().contains("id=\"errormessage\""));
    }
}
