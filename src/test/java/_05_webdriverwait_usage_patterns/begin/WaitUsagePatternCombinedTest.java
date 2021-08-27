package _05_webdriverwait_usage_patterns.begin;

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
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUsagePatternCombinedTest {

    WebDriver driver;

    @BeforeAll
    public static void setupDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void sharedForADriverContext(){

        driver = new ChromeDriver();

        final CollapsePage collapse = new CollapsePage(driver);
        collapse.get();
        collapse.clickAbout();

        Assertions.assertTrue(driver.
                    getCurrentUrl().contains("about.html"));
    }




    private class CollapsePage {
        private final WebDriver driver;
        private final WebDriverWait wait;

        public CollapsePage(final WebDriver driver) {
            this.driver = driver;
            // create a shared wait to use in the
            // context of this page object and driver
            this.wait = new WebDriverWait(driver, 10);
        }

        public void get() {
            driver.get("https://eviltester.github.io/synchole/collapseable.html");
        }

        public void clickAbout() {
            final WebElement section = driver.findElement(
                                        By.cssSelector("section.condense"));
            section.click();

            // wait for, and return, the clickable link
            final WebElement aboutLink = wait.until(
                                        ExpectedConditions.elementToBeClickable(
                                                By.cssSelector("a#aboutlink")));
            aboutLink.click();

            // re-using the wait to wait for the outcome of the click
            wait.until(ExpectedConditions.urlContains("about.html"));
        }
    }

    @AfterEach
    public void closeDriver(){
        driver.close();
    }
}
