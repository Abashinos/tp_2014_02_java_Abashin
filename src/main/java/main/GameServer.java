package main;

import services.UserDAOimpl;
import connectors.DBConnector;
import connectors.DBConnectorMySQL;
import services.AccountService;
import servlets.FrontendServlet;
import org.eclipse.jetty.rewrite.handler.RedirectRegexRule;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import servlets.LoginServlet;
import servlets.SignupServlet;

public class GameServer {

    private int portNumber = 8080;
    private DBConnector dbConnector;

    public GameServer () {
        this(8080);
    }
    public GameServer (int port) {
        this.portNumber = port;
        this.dbConnector = new DBConnectorMySQL();
    }

    public void setDBConnector (DBConnector connector) {
        this.dbConnector = connector;
    }

    public void run() throws Exception {
        Server server = new Server (portNumber);

        server.setHandler(getServerHandlers());

        server.start();
        server.join();
    }

    private HandlerList getServerHandlers() {
        UserDAOimpl userDAO = new UserDAOimpl(dbConnector.getSessionFactory());
        AccountService accountService = new AccountService(userDAO);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new SignupServlet(accountService)), "/signup");
        context.addServlet(new ServletHolder(new LoginServlet(accountService)), "/login");
        context.addServlet(new ServletHolder(new FrontendServlet()), "/*");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("static");

        RewriteHandler rewriteHandler = new RewriteHandler();
        rewriteHandler.setOriginalPathAttribute("requestedPath");

        RedirectRegexRule regRule = new RedirectRegexRule();
        regRule.setRegex("/");
        regRule.setReplacement("/index");
        rewriteHandler.addRule(regRule);

        HandlerList handList = new HandlerList();
        handList.setHandlers(new Handler[]{rewriteHandler, resourceHandler, context});

        return handList;
    }


    public static void main (String[] args) throws Exception {

        GameServer server = new GameServer();
        server.run();
    }
}
