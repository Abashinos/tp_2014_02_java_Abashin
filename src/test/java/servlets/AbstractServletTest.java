package servlets;

import messaging.AddressService;
import messaging.MessageService;
import services.UserSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static supplies.RandomSupply.randomSessionIdGenerator;
import static supplies.RandomSupply.randomStringGenerator;
import static supplies.RandomSupply.randomUserIdGenerator;

public class AbstractServletTest {

    protected static final int AUTH_TIMEOUT = 5000;

    protected static final HttpServletRequest request = mock(HttpServletRequest.class);
    protected static final HttpServletResponse response = mock(HttpServletResponse.class);
    protected static final HttpSession httpSession = mock(HttpSession.class);
    protected static final StringWriter stringWriter = new StringWriter();
    protected static final PrintWriter printWriter = new PrintWriter(stringWriter);

    protected static String generatedTestUsername;
    protected static String generatedTestPassword;
    protected static Long generatedUserID;
    protected static String generatedSessionID;

    protected Map<String, UserSession> sessionMap;

    protected void setUp() throws Exception {
        when(request.getSession()).thenReturn(httpSession);
        when(response.getWriter()).thenReturn(printWriter);

        generatedTestUsername = randomStringGenerator(10);
        generatedTestPassword = randomStringGenerator(10);
        generatedUserID = randomUserIdGenerator();
        generatedSessionID = randomSessionIdGenerator();

        sessionMap = new HashMap<>();
    }

}
