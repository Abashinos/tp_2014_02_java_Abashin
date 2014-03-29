package functional;

import com.sun.istack.internal.NotNull;
import connectors.DBConnection;
import connectors.DBConnectorH2;
import main.GameServer;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import services.AccountService;
import services.UserDAOimpl;

import static supplies.RandomSupply.randomStringGenerator;

public class AuthTest {

    protected static final int PORT_NUMBER = 8880;

    protected static String baseAddress;
    protected static AccountService accountService;
    protected static final GameServer gameServer = new GameServer(PORT_NUMBER);
    protected static Thread thread;

    protected String generatedTestUsername;
    protected String generatedTestPassword;

    @BeforeClass
    public static void setConnectionAndData() throws Exception {
        DBConnection dbConnection = new DBConnection ( new DBConnectorH2() );
        UserDAOimpl userDAO = new UserDAOimpl(dbConnection.getSessionFactory());
        accountService = new AccountService(userDAO);

        gameServer.setDBConnector(dbConnection);
        thread = new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    gameServer.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        baseAddress = "http://localhost:" + PORT_NUMBER;
    }

    public void setUp() throws Exception {
        generatedTestUsername = randomStringGenerator(10);
        generatedTestPassword = randomStringGenerator(10);
    }


    protected boolean executeAuthTest(@NotNull String url,
                                     @NotNull String username,
                                     @NotNull String password) {
        WebDriver webDriver = new HtmlUnitDriver(false);
        webDriver.get(url);

        WebElement usernameElement = webDriver.findElement(By.name("username"));
        usernameElement.sendKeys(username);
        WebElement passwordElement = webDriver.findElement(By.name("password"));
        passwordElement.sendKeys(password);
        WebElement submitElement = webDriver.findElement(By.name("submit"));
        submitElement.submit();

        boolean signupResult = (new WebDriverWait(webDriver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            @NotNull
            public Boolean apply(@NotNull WebDriver d) {
                WebElement el;
                try {
                    el = d.findElement(By.id("userId"));
                }
                catch (NoSuchElementException e) {
                    el = null;
                }
                return el != null;
            }
        });
        webDriver.quit();
        return signupResult;
    }
}
