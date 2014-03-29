package main;

import services.UserDAO;
import connectors.DBConnection;
import connectors.DBConnectorMySQL;
import services.AccountService;
import services.UserDAOimpl;
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

import java.net.BindException;

public class GameServer {

    private int portNumber = 8080;
    private DBConnection dbConnection;

    public GameServer () {
        this(8080);
    }
    public GameServer (int port) {
        this.portNumber = port;
        this.dbConnection = new DBConnection( new DBConnectorMySQL() );
    }

    public void setDBConnection (DBConnection connection) {
        this.dbConnection = connection;
    }

    public void run() throws Exception {
        Server server = new Server (portNumber);

        server.setHandler(getServerHandlers());

        try {
            server.start();
            server.join();
        }
        catch (InterruptedException e) {
            System.out.println("The server is stopping.");
            server.stop();
        }
        catch (BindException e) {
            System.out.print("Fail");
        }
    }

    private HandlerList getServerHandlers() {
        UserDAO userDAO = new UserDAOimpl(dbConnection.getSessionFactory());
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
