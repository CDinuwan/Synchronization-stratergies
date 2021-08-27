package _11_js_abstraction.begin;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitingForJSAbstractionTest {

    WebDriver driver;

    @BeforeAll
    public static void setupDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void waitingJSExample(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/messages.html");

        new WebDriverWait(driver, 20).until(ExpectedConditions.
                jsReturnsValue(
                        "return (window.liveMessages==0 && " +
                                "window.totalRequestsMade>0 ? 'true' : null)"));

        // get total messages and we will compare at end
        final Long totalRequestsSent = (Long) ((JavascriptExecutor) driver).
                                            executeScript(
                                            "return window.totalRequestsMade");


        // wait for all messages to be processed
        ExpectedCondition<Boolean> renderingQueueIsEmpty = driver -> {
            final JavascriptExecutor js = ((JavascriptExecutor) driver);
            String value = (String)js.executeScript(
                                "return (window.totalMessagesReceived>0 " +
                                       "&& window.renderingQueueCount==0 ? 'true' : null)");
            return value!=null;
        };

        new WebDriverWait(driver, 20).until(renderingQueueIsEmpty);

        // get total messages and we will compare at end
        final Long totalMessagesReceived = (Long) ((JavascriptExecutor) driver).
                            executeScript(
                                    "return window.totalMessagesReceived");

        final Long totalDisplayMessages = (Long) ((JavascriptExecutor) driver).
                            executeScript(
                                    "return window.allMessages.length");

        Assertions.assertEquals(
                totalDisplayMessages,
                (totalRequestsSent*2)+totalMessagesReceived);

    }

    @AfterEach
    public void closeDriver(){
        driver.close();
    }
}
