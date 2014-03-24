package servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

public class AbstractServletTest {
    protected static final HttpServletRequest request = mock(HttpServletRequest.class);
    protected static final HttpServletResponse response = mock(HttpServletResponse.class);
    protected static final HttpSession session = mock(HttpSession.class);
    protected static final StringWriter stringWriter = new StringWriter();
    protected static final PrintWriter printWriter = new PrintWriter(stringWriter);

    protected void setUp() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(response.getWriter()).thenReturn(printWriter);
    }

}
