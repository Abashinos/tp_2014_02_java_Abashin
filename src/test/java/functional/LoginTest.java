package functional;

import com.sun.istack.internal.NotNull;
import junit.framework.Assert;
import main.GameServer;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import services.UserAccountTest;

import static supplies.RandomSupply.randomStringGenerator;
import static main.GameServer.dbType;

public class LoginTest {
    private final int PORT_NUMBER = 8880;
    private final GameServer gameServer = new GameServer(PORT_NUMBER, dbType.H2);
    private Thread thread;

    @Test
    public void setUp() throws Exception {
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
    @Test
    public void tearDown() throws Exception {

    }

    @Test
    public void loginTest() throws Exception {
        String username = randomStringGenerator(10);
        String password = randomStringGenerator(10);
        String address = "http://localhost:" + PORT_NUMBER;

        UserAccountTest.registerUser(username, password);
        Assert.assertTrue(executeLoginTest(address, username, password));
        UserAccountTest.deleteUser(username);
    }

    public boolean executeLoginTest (@NotNull String url, @NotNull String username, @NotNull String password) {
        WebDriver webDriver = new HtmlUnitDriver(false);
        webDriver.get(url);

        WebElement usernameElement = webDriver.findElement(By.name("username"));
        usernameElement.sendKeys(username);
        WebElement passwordElement = webDriver.findElement(By.name("password"));
        passwordElement.sendKeys(password);
        WebElement submitElement = webDriver.findElement(By.name("submit"));
        submitElement.submit();

        boolean loginResult = (new WebDriverWait(webDriver, 10)).until(new ExpectedCondition<Boolean>() {
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
        return loginResult;
    }

}
