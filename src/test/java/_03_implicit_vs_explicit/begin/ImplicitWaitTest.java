package _03_implicit_vs_explicit.begin;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ImplicitWaitTest {

    WebDriver driver;

    @BeforeAll
    public static void setupDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @Disabled("test runs in debug mode - needs synchronisation added")
    @Test
    public void implicitlyWaitToSyncOnButtonDisplayAndClick(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/buttons.html");

        final WebElement button00 = driver.findElement(By.id("easy00"));
        button00.click();

        final WebElement button01 = driver.findElement(By.id("easy01"));
        button01.click();

        final WebElement button02 = driver.findElement(By.id("easy02"));
        button02.click();

        final WebElement button03 = driver.findElement(By.id("easy03"));
        button03.click();

        Assertions.assertEquals("All Buttons Clicked",
                driver.findElement(By.id("easybuttonmessage")).getText());
    }

    @Disabled("test runs in debug mode - needs synchronisation added")
    @Test
    public void secondButtonsWithImplicitWait(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/buttons.html");

        final WebElement button00 = driver.findElement(By.id("button00"));
        button00.click();

        final WebElement button01 = driver.findElement(By.id("button01"));
        button01.click();

        final WebElement button02 = driver.findElement(By.id("button02"));
        button02.click();

        final WebElement button03 = driver.findElement(By.id("button03"));
        button03.click();

        Assertions.assertEquals("All Buttons Clicked",
                driver.findElement(By.id("buttonmessage")).getText());
    }

    @AfterEach
    public void closeDriver(){
        driver.close();
    }
}
