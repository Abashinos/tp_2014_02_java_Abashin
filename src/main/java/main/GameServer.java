package main;

import DAO.UserDAOimpl;
import connectors.DBConnector;
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

    private int portNumber = 8080;
    private DBConnector dbConnector = new DBConnector("H2");

    public GameServer () {

    }
    public GameServer(int port) {
        this.portNumber = port;
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
        rewriteHandler.setRewriteRequestURI(true);
        rewriteHandler.setRewritePathInfo(true);
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
