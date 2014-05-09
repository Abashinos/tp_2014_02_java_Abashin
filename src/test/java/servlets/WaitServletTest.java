package servlets;

import messaging.ServletFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import services.UserSession;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WaitServletTest extends AbstractServletTest {

    private static final String REFERER_PAGE = "/evil_intentions";
    private static WaitServlet waitServlet;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        waitServlet = ServletFactory.makeServlet(WaitServlet.class);
        waitServlet.setSessionMap(sessionMap);
    }

    @Test
    public void doGetLoggedInTest() throws Exception {
        when(httpSession.getId()).thenReturn(generatedSessionID);

        UserSession userSession = new UserSession(generatedSessionID, generatedUserID,
                generatedTestUsername, UserSession.Status.OK);
        this.sessionMap.put(generatedSessionID, userSession);

        waitServlet.doGet(request, response);

        Thread.sleep(AUTH_TIMEOUT);

        verify(response, atLeastOnce()).sendRedirect("/timer");
    }

    @Test
    public void doGetNotLoggedInTest() throws Exception {
        when(httpSession.getId()).thenReturn(generatedSessionID);
        when(request.getHeader("referer")).thenReturn(REFERER_PAGE);
        waitServlet.doGet(request, response);

        verify(response, atLeastOnce()).sendRedirect(REFERER_PAGE);
    }

    @Test
    public void doPostTest() throws Exception {
        waitServlet.doPost(request, response);
        verify(response, atLeastOnce()).sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
