package servlets;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;


public class SignupServletTest extends AuthServletTest {

    private static SignupServlet signupServlet;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        signupServlet = new SignupServlet(accountService);
    }

    @Test
    public void getSignupPageLoggedInTest() throws Exception {
        when(session.getAttribute("userId")).thenReturn(0L);
        signupServlet.doGet(request, response);

        Assert.assertTrue(stringWriter.toString().contains("<meta name=\"page\" content=\"signup\">"));
    }
    @Test
    public void getSignupPageNotLoggedInTest() throws Exception {
        when(session.getAttribute("userId")).thenReturn(null);
        signupServlet.doGet(request, response);

        Assert.assertTrue(stringWriter.toString().contains("<meta name=\"page\" content=\"signup\">"));
    }

    @Test
    public void doPostSignupGood() throws Exception {
        signupServlet.doPost(request, response);

        verify(response, atLeastOnce()).sendRedirect("/timer");
        accountService.delete(generatedTestUsername);
    }
    @Test
    public void doPostSignupBad() throws Exception {
        accountService.signup(generatedTestUsername, generatedTestPassword);

        signupServlet.doPost(request, response);

        Assert.assertTrue(stringWriter.toString().contains("id=\"errormessage\""));
        accountService.delete(generatedTestUsername);
    }

}
