package _01_why_sync.end;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class WhyNeedSynchronisationTest {

    WebDriver driver;

    @BeforeAll
    public static void setupDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @Disabled("Test fails until we add Synchronisation - works in debug mode")
    @Test
    public void canNavigateToAboutFromExpandingSectionLink(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/collapseable.html");

        final WebElement section = driver.findElement(
                                    By.cssSelector("section.condense"));
        section.click();

        //Thread.sleep(1000);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

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
