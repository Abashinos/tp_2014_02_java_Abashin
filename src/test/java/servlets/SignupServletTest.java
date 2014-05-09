package servlets;

import messaging.ServletFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import services.UserSession;

import static org.mockito.Mockito.*;


public class SignupServletTest extends AuthServletTest {

    private static SignupServlet signupServlet;
    private static SignupServlet signupServletSpy;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        signupServlet = ServletFactory.makeMServlet(SignupServlet.class);
        signupServlet.setSessionMap(sessionMap);
        signupServletSpy = spy(signupServlet);
    }

    @Test
    public void getSignupPageLoggedInTest() throws Exception {
        when(httpSession.getAttribute("userId")).thenReturn(0L);
        signupServlet.doGet(request, response);

        Assert.assertTrue(stringWriter.toString().contains("<meta name=\"page\" content=\"signup\">"));
    }
    @Test
    public void getSignupPageNotLoggedInTest() throws Exception {
        when(httpSession.getAttribute("userId")).thenReturn(null);
        signupServlet.doGet(request, response);

        Assert.assertTrue(stringWriter.toString().contains("<meta name=\"page\" content=\"signup\">"));
    }

    @Test
    public void doPostSignupGoodTest() throws Exception {
        signupServlet.doPost(request, response);

        Thread.sleep(AUTH_TIMEOUT);

        UserSession userSession = signupServlet.getSessionMap().get(generatedSessionID);

        Assert.assertNotNull(userSession);
        Assert.assertEquals(UserSession.Status.OK, userSession.getStatus());

        accountService.delete(generatedTestUsername);
    }
    @Test
    public void doPostSignupBadTest() throws Exception {
        accountService.signup(generatedTestUsername, generatedTestPassword, generatedSessionID);

        signupServlet.doPost(request, response);

        Thread.sleep(AUTH_TIMEOUT);

        UserSession userSession = signupServlet.getSessionMap().get(generatedSessionID);

        Assert.assertNotNull(userSession);
        Assert.assertEquals(UserSession.Status.ERROR, userSession.getStatus());

        accountService.delete(generatedTestUsername);
    }

    @Test
    public void doPostSignupDbErrorTest() throws Exception {
        doReturn(messageService).when(signupServletSpy).getMessageService();
        doReturn(addressService).when(messageService).getAddressService();
        doReturn(null).when(addressService).getAccountServiceAddress();

        signupServletSpy.doPost(request, response);
        String errorMessage = signupServletSpy.getSessionMap().get(generatedSessionID).getErrorMessage();

        Assert.assertEquals("DB error", errorMessage);
    }
}
