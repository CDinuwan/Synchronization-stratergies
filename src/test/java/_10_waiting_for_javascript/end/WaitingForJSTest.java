package _10_waiting_for_javascript.end;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
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
                ExpectedConditions.jsReturnsValue(
                        "return (window.totalMessagesReceived>0 && "
                                +
                                "window.renderingQueueCount==0 ? 'true' : null)"));

        new WebDriverWait(driver,20).until(
                ExpectedConditions.textToBePresentInElementLocated(
                        By.id("messagecount"),
                        "Message Count: 0 : 0"));

        Assertions.assertEquals("Message Count: 0 : 0",
                driver.findElement(By.id("messagecount")).getText());
    }

    @Test
    public void waitingExampleUsingJavaClosures(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/messages.html");

        ExpectedCondition renderingQueueIsEmpty = driver ->{
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String value = (String)js.executeScript(
                    "return (window.totalMessagesReceived>0 && "
                            +
                            "window.renderingQueueCount==0 ? 'true' : null)");
            return value!=null;
        };

        new WebDriverWait(driver,20).until(renderingQueueIsEmpty);

//        new WebDriverWait(driver,20).until(
//                ExpectedConditions.jsReturnsValue(
//                        "return (window.totalMessagesReceived>0 && "
//                                +
//                                "window.renderingQueueCount==0 ? 'true' : null)"));

        new WebDriverWait(driver,20).until(
                ExpectedConditions.textToBePresentInElementLocated(
                        By.id("messagecount"),
                        "Message Count: 0 : 0"));

        Assertions.assertEquals("Message Count: 0 : 0",
                driver.findElement(By.id("messagecount")).getText());
    }


    @Test
    public void waitingExampleUsingAsyncAsATimeout(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/messages.html");

        JavascriptExecutor exec = (JavascriptExecutor) driver;
        exec.executeAsyncScript(
                "window.onMessageQueueEmpty(arguments[arguments.length-1])");

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
