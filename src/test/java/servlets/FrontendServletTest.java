package servlets;

import messaging.ServletFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import services.UserSession;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

public class FrontendServletTest extends AbstractServletTest {

    private static FrontendServlet frontendServlet;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        frontendServlet = ServletFactory.makeServlet(FrontendServlet.class);
        frontendServlet.setSessionMap(sessionMap);
    }

    @Test
    public void getIndexPageLoggedInTest() throws Exception {
        getLoggedInPageTest("/index");
    }

    @Test
    public void getIndexPageNotLoggedInTest() throws Exception {
        getNotLoggedInPageTest("/index");
    }


    @Test
    public void getTimerPageLoggedInTest() throws Exception {
        getLoggedInPageTest("/timer");
    }

    @Test
    public void getTimerPageNotLoggedInTest() throws Exception {
        getNotLoggedInPageTest("/timer");
    }

    @Test
    public void doPostTest() throws Exception {
        frontendServlet.doPost(request, response);
        verify(response, atLeastOnce()).sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    private void getLoggedInPageTest(String page) throws Exception {
        when(httpSession.getId()).thenReturn(generatedSessionID);
        when(request.getRequestURI()).thenReturn(page);

        UserSession userSession = new UserSession(generatedSessionID, generatedUserID,
                generatedTestUsername, UserSession.Status.OK);
        this.sessionMap.put(generatedSessionID, userSession);

        frontendServlet.doGet(request, response);

        Assert.assertTrue(stringWriter.toString().contains("<meta name=\"page\" content=\"index\">"));
    }

    private void getNotLoggedInPageTest(String page) throws Exception {
        when(request.getRequestURI()).thenReturn(page);
        frontendServlet.doGet(request, response);

        verify(response, atLeastOnce()).sendRedirect("/login");
    }
}