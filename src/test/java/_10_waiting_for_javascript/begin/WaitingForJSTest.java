package _10_waiting_for_javascript.begin;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitingForJSTest {

    WebDriver driver;

    @BeforeAll
    public static void setupDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void waitingExampleUsingExpectedConditions(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/messages.html");

        new WebDriverWait(driver,20).until(
                ExpectedConditions.textToBePresentInElementLocated(
                                            By.id("messagecount"),
                                            "Message Count: 0 : 0"));

        Assertions.assertEquals("Message Count: 0 : 0",
                driver.findElement(By.id("messagecount")).getText());
    }

    @AfterEach
    public void closeDriver(){
        driver.close();
    }


}
