package functional;

import com.sun.istack.internal.NotNull;
import connectors.DBConnection;
import connectors.DBConnectorH2;
import main.GameServer;
import messaging.ServletFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import services.AccountService;

import static supplies.RandomSupply.*;

public abstract class AuthTest {

    protected static final int PORT_NUMBER = 8880;

    protected static AccountService accountService;
    protected static GameServer gameServer;
    protected static Thread thread;

    protected String address = "http://localhost:" + PORT_NUMBER;
    protected String generatedTestUsername;
    protected String generatedTestPassword;
    protected String generatedSessionId;


    @BeforeClass
    public static void setConnectionAndData() throws Exception {
        DBConnection dbConnection = new DBConnection(new DBConnectorH2());
        accountService = ServletFactory.makeAccountService(dbConnection);

        gameServer = new GameServer(PORT_NUMBER);
        gameServer.setDBConnection(dbConnection);
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

    @AfterClass
    public static void stopConnection() throws Exception {
        thread.interrupt();
    }

    public void setUp() throws Exception {
        generatedTestUsername = randomStringGenerator(10);
        generatedTestPassword = randomStringGenerator(10);
        generatedSessionId = randomSessionIdGenerator();
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

        boolean signupResult;
        try {
            signupResult = (new WebDriverWait(webDriver, 10)).until(new ExpectedCondition<Boolean>() {
                @Override
                @NotNull
                public Boolean apply(@NotNull WebDriver d) {
                    WebElement el;
                    try {
                        el = d.findElement(By.id("userId"));
                    } catch (NoSuchElementException e) {
                        el = null;
                    }
                    return el != null;
                }
            });
        }
        catch (Exception ignored) {
            return false;
        }
        webDriver.quit();
        return signupResult;
    }
}
