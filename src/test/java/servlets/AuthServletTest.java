package servlets;

import connectors.DBConnection;
import connectors.DBConnectorH2;
import messaging.AddressService;
import messaging.MessageService;
import messaging.ServletFactory;
import services.AccountService;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class AuthServletTest extends AbstractServletTest {

    protected static AccountService accountService;
    protected final MessageService messageService = spy(new MessageService());
    protected final AddressService addressService = spy(new AddressService());

    protected void setUp() throws Exception {
        super.setUp();

        accountService = ServletFactory.makeAccountService(new DBConnection(new DBConnectorH2()));
        when(request.getParameter("username")).thenReturn(generatedTestUsername);
        when(request.getParameter("password")).thenReturn(generatedTestPassword);
        when(httpSession.getId()).thenReturn(generatedSessionID);
        when(request.getParameter("password")).thenReturn(generatedTestPassword);
    }
}
