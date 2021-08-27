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


/*

Illustrate patterns:

- new WebDriverWait for every sync point
- shared local context wait
- wait and return a value
- wait as an alternative to an assertion
- shared driver context wait

 */
public class WaitUsagePatternsTest {

    WebDriver driver;

    @BeforeAll
    public static void setupDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void waitForEverySyncPoint() throws InterruptedException {
        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/collapseable.html");

        final WebElement section = driver.findElement(By.cssSelector("section.condense"));
        section.click();

        final By linkToClick = By.cssSelector("a#aboutlink");

        Thread.sleep(3000);

        final WebElement aboutLink = driver.findElement(linkToClick);
        aboutLink.click();

        Assertions.assertTrue(driver.getCurrentUrl().contains("about.html"));
    }

    @AfterEach
    public void closeDriver(){
        driver.close();
    }

}
