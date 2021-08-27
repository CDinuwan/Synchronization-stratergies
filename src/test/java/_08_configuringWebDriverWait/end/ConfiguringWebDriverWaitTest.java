package _08_configuringWebDriverWait.end;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ConfiguringWebDriverWaitTest {

    WebDriver driver;

    @BeforeAll
    public static void setupDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void ExpectedConditionsClickableLink(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/collapseable.html");

        final WebElement alumniImage = driver.findElement(By.cssSelector("section.condense"));
        alumniImage.click();

        final By linkToClick = By.cssSelector("a#aboutlink");

        WaitFor.clickableLink(driver).
                until(ExpectedConditions.elementToBeClickable(linkToClick));

        driver.findElement(linkToClick).click();
        Assertions.assertTrue(driver.getCurrentUrl().contains("about.html"));
    }


    @AfterEach
    public void closeDriver(){
        driver.close();
    }

    private static class WaitFor {
        public static WebDriverWait clickableLink(WebDriver driver) {
            return (WebDriverWait) new WebDriverWait(driver, 10).
                    pollingEvery(Duration.ofMillis(100)).
                    withMessage("Could not find a clickable link").
                    withTimeout(Duration.ofSeconds(5)).
                    ignoring(NullPointerException.class);
        }
    }
}
