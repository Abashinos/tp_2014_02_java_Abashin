package main;

import DAO.UserDAOimpl;
import connectors.DBConnector;
import connectors.DBConnectorH2;
import connectors.DBConnectorMySQL;
import servlets.Frontend;
import org.eclipse.jetty.rewrite.handler.RedirectRegexRule;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import servlets.SignupServlet;
import services.UserAccount;

public class GameServer {

    public enum dbType {MySQL, H2}

    private int portNumber = 8080;
    private DBConnector dbConnector;

    public GameServer () {
        this.dbConnector = new DBConnectorMySQL();
    }
    public GameServer (int port, dbType db) {
        this.portNumber = port;
        switch (db) {
            case H2:
                this.dbConnector = new DBConnectorH2();
                break;
            case MySQL:
            default:
                this.dbConnector = new DBConnectorMySQL();
        }
    }
    public GameServer (dbType db) {
        this(8080, db);
    }


    public void run() throws Exception {
        Server server = new Server (portNumber);

        server.setHandler(getServerHandlers());

        server.start();
        server.join();
    }

    private HandlerList getServerHandlers() {
        Frontend frontend = new Frontend();
        UserDAOimpl userDAO = new UserDAOimpl(dbConnector.getSessionFactory());
        UserAccount userAccount = new UserAccount(userDAO);
        SignupServlet signupServlet = new SignupServlet(userAccount);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(signupServlet), "/signup");
        context.addServlet(new ServletHolder(signupServlet), "/login");
        context.addServlet(new ServletHolder(frontend), "/*");

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

        GameServer server = new GameServer(dbType.MySQL);
        server.run();
    }
}
