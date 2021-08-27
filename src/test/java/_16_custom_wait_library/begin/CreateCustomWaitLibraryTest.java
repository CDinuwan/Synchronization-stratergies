package _16_custom_wait_library.begin;

import _16_custom_wait_library.MyExpectedConditions;
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

public class CreateCustomWaitLibraryTest {

    WebDriver driver;

    @BeforeAll
    public static void setupDriver(){
        WebDriverManager.chromedriver().setup();
    }
    
    @Test
    public void canClickOnSecondButtons(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/buttons.html");

        final WebDriverWait wait = new WebDriverWait(driver, 10);

        final WebElement startButton = wait.until(ExpectedConditions.
                elementToBeClickable(By.id("button00")));
        startButton.click();

        final WebElement buttonOne = wait.until(ExpectedConditions.
                                            elementToBeClickable(By.id("button01")));
        buttonOne.click();

        final WebElement buttonTwo = wait.until(ExpectedConditions.
                                        elementToBeClickable(By.id("button02")));
        buttonTwo.click();

        final WebElement buttonThree = wait.until(ExpectedConditions.
                elementToBeClickable(By.id("button03")));
        buttonThree.click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("buttonmessage"), "All Buttons"));

        Assertions.assertEquals("All Buttons Clicked",
                driver.findElement(By.id("buttonmessage")).getText());
    }

    @Test
    public void expectedConditionsClickableLink(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/collapseable.html");

        final WebElement section = driver.findElement(By.cssSelector("section.condense"));
        section.click();

        final By linkToClick = By.cssSelector("a#aboutlink");
        final By expandingElement = By.cssSelector("section.condense");

        new WebDriverWait(driver, 10).
                until(new MyExpectedConditions().elementHasExpandedFully(expandingElement));

        driver.findElement(linkToClick).click();

        Assertions.assertTrue(driver.getCurrentUrl().contains("about.html"));
    }

    @AfterEach
    public void closeDriver(){
        driver.close();
    }

}
