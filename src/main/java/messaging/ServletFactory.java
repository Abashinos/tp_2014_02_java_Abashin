package messaging;

import connectors.DBConnection;
import services.AccountService;
import services.UserDAO;
import services.UserDAOimpl;
import services.UserSession;
import servlets.AbstractMServlet;
import servlets.AbstractServlet;

import java.util.HashMap;
import java.util.Map;

public class ServletFactory {

    private static Map<String, UserSession> sessionMap = new HashMap<>();
    private static MessageService messageService = new MessageService();

    public static AccountService makeAccountService (DBConnection dbConnection) {
        UserDAO dao = new UserDAOimpl(dbConnection.getSessionFactory());
        AccountService accountService = new AccountService(dao, messageService);
        (new Thread(accountService)).start();
        return accountService;
    }

    public static <TServlet extends AbstractServlet>
            TServlet makeServlet(Class<TServlet> servletClass)
                throws InstantiationException, IllegalAccessException {

        TServlet servlet = servletClass.newInstance();
        servlet.setSessionMap(sessionMap);
        servlet.setPagesData();
        return servlet;

    }

    public static <TServlet extends AbstractMServlet>
            TServlet makeMServlet(Class<TServlet> servletClass)
                throws InstantiationException, IllegalAccessException {

        TServlet servlet = servletClass.newInstance();
        servlet.setSessionMap(sessionMap);
        servlet.setPagesData();
        servlet.setMessageService(messageService);
        (new Thread(servlet)).start();
        return servlet;
    }
}
