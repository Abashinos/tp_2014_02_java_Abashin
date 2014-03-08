package servlets;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FrontendTest {
    private static final HttpServletRequest request = mock(HttpServletRequest.class);
    private static final HttpServletResponse response = mock(HttpServletResponse.class);
    private static final HttpSession session = mock(HttpSession.class);
    private static Frontend frontend;
    private static final StringWriter stringWriter = new StringWriter();
    private static final PrintWriter printWriter = new PrintWriter(stringWriter);

    @Before
    public void setUp() throws Exception {
        frontend = new Frontend();

        when(request.getSession()).thenReturn(session);
        when(response.getWriter()).thenReturn(printWriter);
        when(session.getAttribute("userId")).thenReturn(0L);
    }


    @Test
    public void getRootPageLoggedInTest() throws Exception {
        when(request.getRequestURI()).thenReturn("/index");
        when(request.getServletPath()).thenReturn("");
        frontend.doGet(request, response);

        Assert.assertTrue(stringWriter.toString().contains("<meta name=\"page\" content=\"index\">"));
    }
}