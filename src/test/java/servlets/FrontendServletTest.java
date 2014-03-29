package servlets;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class FrontendServletTest extends AbstractServletTest {

    private static FrontendServlet frontendServlet;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        frontendServlet = new FrontendServlet();

        when(request.getServletPath()).thenReturn("");
    }

    @Test
    public void getIndexPageLoggedInTest() throws Exception {
        when(request.getRequestURI()).thenReturn("/index");
        when(session.getAttribute("userId")).thenReturn(0L);
        frontendServlet.doGet(request, response);

        Assert.assertTrue(stringWriter.toString().contains("<meta name=\"page\" content=\"index\">"));
    }

    @Test
    public void getIndexPageNotLoggedInTest() throws Exception {
        when(request.getRequestURI()).thenReturn("/index");
        when(session.getAttribute("userId")).thenReturn(null);
        frontendServlet.doGet(request, response);

        verify(response, atLeastOnce()).sendRedirect("/login");
    }



    @Test
    public void getTimerPageLoggedInTest() throws Exception {
        when(request.getRequestURI()).thenReturn("/timer");
        when(session.getAttribute("userId")).thenReturn(0L);
        frontendServlet.doGet(request, response);

        Assert.assertTrue(stringWriter.toString().contains("<meta name=\"page\" content=\"timer\">"));
    }

    @Test
    public void getTimerPageNotLoggedInTest() throws Exception {
        when(request.getRequestURI()).thenReturn("/timer");
        when(session.getAttribute("userId")).thenReturn(null);
        frontendServlet.doGet(request, response);

        verify(response, atLeastOnce()).sendRedirect("/login");
    }

}