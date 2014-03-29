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
        accountService.signup(generatedTestUsername, generatedTestPassword);

        loginServlet.doPost(request, response);

        verify(response, atLeastOnce()).sendRedirect("/timer");
        accountService.delete(generatedTestUsername);
    }

    @Test
    public void doPostLoginBad() throws Exception {

        when(request.getParameter("username")).thenReturn(generatedTestUsername);
        when(request.getParameter("password")).thenReturn(generatedTestPassword);
        loginServlet.doPost(request, response);

        Assert.assertTrue(stringWriter.toString().contains("id=\"errormessage\""));
    }
}