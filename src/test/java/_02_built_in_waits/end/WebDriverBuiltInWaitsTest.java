package _02_built_in_waits.end;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

class WebDriverBuiltInWaitsTest {

    WebDriver driver;

    @BeforeAll
    public static void setupDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void builtInSyncOnPageLoad(){

        driver = new ChromeDriver();

        // loading a page has an automatic sync on contents loaded
        driver.get("https://eviltester.github.io/synchole/collapseable.html");

        // WebDriver automatically waits for the page to load
        Assertions.assertEquals("SyncHole",
                driver.findElement(
                        By.cssSelector("h2")).getText());
    }

    //submit on a form submit will wait for a new page to load
    @Test
    public void submitResultsPageLoadWait(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/form.html");

        final WebElement username = driver.findElement(By.name("username"));
        username.sendKeys("bob");
        username.submit();

        Assertions.assertEquals("Thanks For Your Submission",
                driver.findElement(By.id("thanks")).getText()
        );
    }

    // WebDriver does not wait for JavaScript to complete so if JavaScript
    // populated anything on the page then we have to wait for it separately
    @Test
    public void doesNotSyncOnJavaScriptExecutionUsernamePopulation()
            throws InterruptedException {

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/form.html");

        final WebElement username = driver.findElement(By.name("username"));
        username.sendKeys("bob");
        username.submit();


        Assertions.assertEquals("Thanks For Your Submission",
                driver.findElement(By.id("thanks")).getText()
        );

        Thread.sleep(2000);

        Assertions.assertEquals("bob",
                driver.findElement(By.cssSelector("li[data-name='username']")).
                        getAttribute("data-value"));
    }



    /*
        WebDriver waits for HTTP page load event
             - HTML, CSS loading, Image loading
        Represented by the 'complete' readystate
     */
    @Test
    public void whichReadyState() throws InterruptedException {

        driver = new ChromeDriver();

        // loading a page has an automatic sync on contents loaded
        String url = "https://eviltester.github.io/synchole/results.html";
                url = url + "?username=bob&submitbutton=submit";

        driver.get(url);

        String readyState = (String)((JavascriptExecutor)driver).
                            executeScript("return document.readyState;");

        // webdriver waits until page readyState is complete,
        // which is prior to the JavaScript on load event firing
        // but after images and stylesheets have loaded
        Assertions.assertEquals("complete", readyState);

        // without some sort of wait
        Thread.sleep(2000);

        // this will fail
        Assertions.assertEquals("bob",
                driver.findElement(By.cssSelector("li[data-name='username']")).
                        getAttribute("data-value"));

    }

    @AfterEach
    public void closeDriver(){
        driver.close();
    }
}
