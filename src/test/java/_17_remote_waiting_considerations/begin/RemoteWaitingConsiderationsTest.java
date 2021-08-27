package _17_remote_waiting_considerations.begin;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class RemoteWaitingConsiderationsTest {

    WebDriver driver;

    /*
        If you want to run this test then you can sign up for a trial
        of SauceLabs and use your account information in these constants
        You might need to check that `getRemoteDriver` still matches
        the SauceLabs instructions for creating a remote connection.

        Also SauceLabs is only one example of a cloud hosted provider.

        You can setup trial accounts with:

        - SauceLabs - http://saucelabs.com/
        - BrowserStack - https://www.browserstack.com/
        - GridLastic - https://www.gridlastic.com/
        - Zalenium - https://opensource.zalando.com/zalenium/
        - CrossBrowserTesting - http://crossbrowsertesting.com/
        - LambdaTest - https://www.lambdatest.com/
        - or any other cloud hosting grid supplier.
    */

    String SAUCE_USERNAME="seleniumsimplified"; // "your username"
    String SAUCE_ACCESS_KEY="b5cba549-a427-4888-a191-6c587ebef34f"; // "your access key"

    @BeforeAll
    public static void setupDriver(){
        WebDriverManager.chromedriver().setup();
    }

    public WebDriver getRemoteDriver() {
        // this is configured for SauceLabs
        // if you use a different cloud vendor then use their
        // support docs to find the connection code
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", SAUCE_USERNAME);
        sauceOptions.setCapability("accessKey", SAUCE_ACCESS_KEY);

        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setExperimentalOption("w3c", true);
        browserOptions.setCapability("platformName", "Windows 10");
        browserOptions.setCapability("browserVersion", "latest");
        browserOptions.setCapability("sauce:options", sauceOptions);
        String sauceURL = "https://ondemand.saucelabs.com/wd/hub";
        URL url = null;
        try {
            url = new URL(sauceURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new RemoteWebDriver(url, browserOptions);
    }

    @Test
    public void checkProgressBars() {

        // this test works locally
        driver = new ChromeDriver();
        // this test fails remotely
        //driver = getRemoteDriver();

        driver.get("https://eviltester.github.io/synchole/shortlived.html");

        Assertions.assertTrue(getProgressFor("progressbar0") > 0,
                "task 1 should have started");

        Assertions.assertTrue(getProgressFor("progressbar1") > 0,
                "task 2 should have started");

        Assertions.assertTrue(getProgressFor("progressbar2") < 100,
                "task 3 should not have started");

        final WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.
                attributeToBe(By.id("progressbar0"), "value", "50"));
        wait.until(ExpectedConditions.
                attributeToBe(By.id("progressbar0"), "value", "100"));
        wait.until(ExpectedConditions.
                attributeToBe(By.id("progressbar1"), "value", "50"));
        wait.until(ExpectedConditions.
                attributeToBe(By.id("progressbar1"), "value", "100"));
        wait.until(ExpectedConditions.
                attributeToBe(By.id("progressbar2"), "value", "50"));
        wait.until(ExpectedConditions.
                attributeToBe(By.id("progressbar2"), "value", "100"));
    }

    private int getProgressFor(final String barid) {
        String value =driver.findElement(By.id(barid)).getAttribute("value");

        return Math.round(Float.parseFloat(value));
    }

    @AfterEach
    public void closeDriver(){
        driver.quit();
    }
}
