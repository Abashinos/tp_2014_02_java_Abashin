package servlets;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginServletTest extends AuthServletTest {

    private static LoginServlet loginServlet;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        loginServlet = new LoginServlet(accountService);
    }

    @Test
    public void getLoginPageLoggedInTest() throws Exception {
        when(session.getAttribute("userId")).thenReturn(0L);
        loginServlet.doGet(request, response);

        Assert.assertTrue(stringWriter.toString().contains("<meta name=\"page\" content=\"login\">"));
    }

    @Test
    public void getLoginPageNotLoggedInTest() throws Exception {
        when(session.getAttribute("userId")).thenReturn(null);
        loginServlet.doGet(request, response);

        Assert.assertTrue(stringWriter.toString().contains("<meta name=\"page\" content=\"login\">"));
    }

    @Test
    public void doPostLoginGood() throws Exception {
        accountService.signup(TEST_USERNAME, TEST_PASSWORD);

        loginServlet.doPost(request, response);

        verify(response, atLeastOnce()).sendRedirect("/timer");
        accountService.delete(TEST_USERNAME);
    }

    @Test
    public void doPostLoginBad() throws Exception {

        when(request.getParameter("username")).thenReturn(TEST_USERNAME);
        when(request.getParameter("password")).thenReturn(TEST_PASSWORD);
        loginServlet.doPost(request, response);

        Assert.assertTrue(stringWriter.toString().contains("id=\"errormessage\""));
    }
}
