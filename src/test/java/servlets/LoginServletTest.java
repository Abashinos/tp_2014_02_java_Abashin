package servlets;

import messaging.ServletFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import services.UserSession;

import static org.mockito.Mockito.*;

public class LoginServletTest extends AuthServletTest {

    private static LoginServlet loginServlet;
    private static LoginServlet loginServletSpy;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        loginServlet = ServletFactory.makeMServlet(LoginServlet.class);
        loginServlet.setSessionMap(sessionMap);
        loginServletSpy = spy(loginServlet);

    }

    @Test
    public void getLoginPageLoggedInTest() throws Exception {
        when(httpSession.getAttribute("userId")).thenReturn(0L);
        loginServlet.doGet(request, response);

        Assert.assertTrue(stringWriter.toString().contains("<meta name=\"page\" content=\"login\">"));
    }

    @Test
    public void getLoginPageNotLoggedInTest() throws Exception {
        when(httpSession.getAttribute("userId")).thenReturn(null);
        loginServlet.doGet(request, response);

        Assert.assertTrue(stringWriter.toString().contains("<meta name=\"page\" content=\"login\">"));
    }

    @Test
    public void doPostLoginGoodTest() throws Exception {
        accountService.signup(generatedTestUsername, generatedTestPassword, generatedSessionID);

        loginServlet.doPost(request, response);

        Thread.sleep(AUTH_TIMEOUT);

        UserSession userSession = loginServlet.getSessionMap().get(generatedSessionID);

        Assert.assertNotNull(userSession);
        Assert.assertEquals(UserSession.Status.OK, userSession.getStatus());

        accountService.delete(generatedTestUsername);
    }

    @Test
    public void doPostLoginBadTest() throws Exception {

        loginServlet.doPost(request, response);

        Thread.sleep(AUTH_TIMEOUT);

        UserSession.Status userStatus = loginServlet.getSessionMap().get(generatedSessionID).getStatus();
        Assert.assertEquals(UserSession.Status.ERROR, userStatus);
    }

    @Test
    public void doPostLoginDbErrorTest() throws Exception {
        doReturn(messageService).when(loginServletSpy).getMessageService();
        doReturn(addressService).when(messageService).getAddressService();
        doReturn(null).when(addressService).getAccountServiceAddress();

        loginServletSpy.doPost(request, response);
        String errorMessage = loginServletSpy.getSessionMap().get(generatedSessionID).getErrorMessage();

        Assert.assertEquals("DB error", errorMessage);
    }
}
