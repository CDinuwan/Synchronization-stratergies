package _01_why_sync.begin;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WhyNeedSynchronisationTest {

    WebDriver driver;

    @BeforeAll
    public static void setupDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @Disabled("Test fails until we add Synchronisation - works in debug mode")
    @Test
    public void canNavigateToAboutFromExpandingSectionLink() throws InterruptedException {

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/collapseable.html");

        final WebElement section = driver.findElement(
                                    By.cssSelector("section.condense"));
        section.click();

        Thread.sleep(1000);

        final WebElement sectionLink = driver.findElement(
                                        By.cssSelector("a#aboutlink"));
        sectionLink.click();

        Assertions.assertTrue(driver.getCurrentUrl().contains("about.html"));
    }

    @AfterEach
    public void closeDriver(){
        driver.close();
    }
}
