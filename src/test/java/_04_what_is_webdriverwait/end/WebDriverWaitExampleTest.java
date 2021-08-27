package _04_what_is_webdriverwait.end;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class WebDriverWaitExampleTest {

    WebDriver driver;

    @BeforeAll
    public static void setupDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void handleCollapseableWithWebDriverWait(){
        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/collapseable.html");

        final WebElement section = driver.findElement(By.cssSelector("section.condense"));

        section.click();

        final By linkToClick = By.cssSelector("a#aboutlink");

        //driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        new WebDriverWait(driver, 4).
                until(ExpectedConditions.
                        elementToBeClickable(linkToClick));

        final WebElement link = driver.findElement(linkToClick);
        link.click();

        Assertions.assertTrue(driver.getCurrentUrl().contains("about.html"));
    }

    // todo tidy this test
    @Test
    public void canClickOnSecondButtonsWithWebDriverWait(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/buttons.html");

        clickOnDynamicButton(By.id("button00"));

        clickOnDynamicButton(By.id("button01"));

        clickOnDynamicButton(By.id("button02"));

        clickOnDynamicButton(By.id("button03"));

        Assertions.assertEquals("All Buttons Clicked",
                driver.findElement(By.id("buttonmessage")).getText());
    }

    private void clickOnDynamicButton(By locator) {
        WebElement button;
        new WebDriverWait(driver, 10).
                until(ExpectedConditions.elementToBeClickable(locator));
        button = driver.findElement(locator);
        button.click();
    }

    @AfterEach
    public void closeDriver(){
        driver.close();
    }
}
