package functional;

import com.sun.istack.internal.NotNull;
import connectors.DBConnector;
import connectors.DBConnectorH2;
import main.GameServer;
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

    protected final static int PORT_NUMBER = 8880;
    protected static String TEST_USERNAME;
    protected static String TEST_PASSWORD;

    protected static AccountService accountService;

    protected final GameServer gameServer = new GameServer(PORT_NUMBER);
    protected Thread thread;

    public void setUp() throws Exception {
        DBConnector dbConnector = new DBConnectorH2();
        UserDAOimpl userDAO = new UserDAOimpl(dbConnector.getSessionFactory());
        accountService = new AccountService(userDAO);

        TEST_USERNAME = randomStringGenerator(10);
        TEST_PASSWORD = randomStringGenerator(10);

        gameServer.setDBConnector(new DBConnectorH2());
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
