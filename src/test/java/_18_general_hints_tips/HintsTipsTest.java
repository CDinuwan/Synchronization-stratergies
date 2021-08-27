package _18_general_hints_tips;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class HintsTipsTest {

    private WebDriver driver;

    public static void setupDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void inbuiltSynchronisationOnPageLoadAndSubmit(){

        driver = new ChromeDriver();

        /*
            loading a page has an automatic sync
            on contents loaded

            also waits after form submission

            does not wait for JavaScript
         */

        driver.get("https://eviltester.github.io/synchole/collapseable.html");

        Assertions.assertEquals("SyncHole",
                driver.findElement(
                        By.cssSelector("h2")).getText());
    }

    private class ButtonPage {

        /*
            Page Factory allocated elements
            wait till present in the DOM
         */

        @FindBy(how = How.ID, using = "easy00")
        public WebElement startButton;

        @FindBy(how = How.ID, using = "easy01")
        public WebElement button01;

        public ButtonPage(WebDriver driver){
            PageFactory.initElements(driver, this);
        }
    }

    @Test
    public void avoidImplicitWaits(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/buttons.html");

        ButtonPage page = new ButtonPage(driver);

        /*
            Avoid implicit waits as it slows down test failures
         */
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        page.startButton.click();
        page.button01.click();
    }

    @Test
    public void useExplicitWaitsWithWebDriverWaitExpectedConditions(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/buttons.html");

        /*
            use WebDriverWait explicit waits and ExpectedConditions
         */
        new WebDriverWait(driver,10).until(
                ExpectedConditions.elementToBeClickable(
                        By.id("button00"))).
                click();

        /*
            FluentWait can handle non-WebDriverWaits,
            also consider Awaitility
         */
    }

    @Test
    public void useWaitsAsAnAlternativeToAssertsSparingly(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/collapseable.html");

        /*
            a wait that fails will fail the test
            we might be tempted to use that as an alternative
            to an assertion
         */

        new WebDriverWait(driver,10).until(
                ExpectedConditions.
                        textToBePresentInElementLocated(
                                By.cssSelector("h2"), "SyncHole")
        );

//        Assertions.assertEquals("SyncHole",
//                driver.findElement(
//                        By.cssSelector("h2")).getText());
    }


    private class Waiting {
        private final WebDriver myDriver;
        private final int timeout;
        final WebDriverWait wait;

        public Waiting(final WebDriver driver) {
            this.myDriver = driver;
            this.timeout = 10;

            wait = new WebDriverWait(myDriver, timeout);
        }

        public WebElement untilClickable(final By locator) {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        }
    }

    @Test
    public void waitUsingLibraries(){

        driver = new ChromeDriver();
        driver.get("https://eviltester.github.io/synchole/buttons.html");

        new Waiting(driver).
                untilClickable(By.id("button00")).
                click();
    }

    private class ButtonComponent {
        private final By locator;
        private final WebDriver myDriver;

        public ButtonComponent(WebDriver myDriver, final By elementLocator) {
            this.myDriver = myDriver;
            this.locator = elementLocator;
        }

        public void waitTillReady() {
            new WebDriverWait(this.myDriver,10).
                    until(
                            ExpectedConditions.elementToBeClickable(this.locator));
        }

        // Add synchronisation into the Page Objects and components
        public void click() {
            waitTillReady();
            this.myDriver.findElement(this.locator).click();
        }
    }

    @Test
    public void synchroniseInComponents() {

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/buttons.html");

        ButtonComponent startButton = new ButtonComponent(driver, By.id("button00"));
        startButton.click();

        // SlowLoadableComponent from Support classes provides sync
        // Create your own page Objects and Components with sync code
    }

    @Test
    public void waitingExampleUsingJavaClosures(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/collapseable.html");

        driver.findElement(
                By.cssSelector("section.condense")).click();

        final WebElement link = driver.findElement(By.id("aboutlink"));

        /*
            JavaScript can be used with JavascriptExecutor to
            help us check for internal application state
         */
        ExpectedCondition<Boolean> linkIsVisible = driver -> {
            final JavascriptExecutor js = ((JavascriptExecutor) driver);
            // check for visibility by checking if body is top most
            return (Boolean) js.executeScript(
                "var rect=arguments[0].getBoundingClientRect();" +
                    " var surround = document.elementFromPoint(rect.x, rect.y).tagName;"+
                        "return surround.toLowerCase()!=='body';"
                    , link );
        };

        new WebDriverWait(driver, 20).until(linkIsVisible);
        link.click();

        Assertions.assertTrue(driver.getCurrentUrl().contains("about.html"));

    }

    @AfterEach
    public void closeDriver(){
        driver.quit();
    }
}
