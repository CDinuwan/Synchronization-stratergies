package _16_custom_wait_library.end;

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
import org.openqa.selenium.support.ui.ExpectedCondition;
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

        Waiting wait = new Waiting(driver);

        final WebElement startButton = wait.untilClickable(By.id("button00"));
        startButton.click();

        final WebElement buttonOne = wait.untilClicked(By.id("button01"));
        wait.untilClicked(By.id("button02"));
        wait.untilClicked(By.id("button03"));

        wait.untilTextContains("All Buttons", By.id("buttonmessage"));

        Assertions.assertEquals("All Buttons Clicked",
                driver.findElement(By.id("buttonmessage")).getText());
    }

    @Test
    public void expectedConditionsClickableLink(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/collapseable.html");

        Waiting wait = new Waiting(driver);
        wait.untilClicked(By.cssSelector("section.condense"));

        final By linkToClick = By.cssSelector("a#aboutlink");
        final By expandingElement = By.cssSelector("section.condense");

        wait.until(wait.condition().elementHasExpandedFully(expandingElement));

        wait.untilClicked(linkToClick);

        wait.until(ExpectedConditions.urlContains("about.html"));
    }

    @AfterEach
    public void closeDriver(){
        driver.close();
    }

    private class Waiting {
        private final WebDriver myDriver;
        private final int timeout;
        private final WebDriverWait wait;

        public Waiting(WebDriver driver) {
            this.myDriver = driver;
            this.timeout = 10;
            wait = new WebDriverWait(myDriver, timeout);
        }

        public WebElement untilClickable(By locator) {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        }

        public WebElement untilClicked(By locator) {
            WebElement elem = untilClickable(locator);
            elem.click();
            return elem;
        }

        public WebElement untilTextContains(String partialText, By locator) {
            WebElement elem = wait.until(ExpectedConditions.
                    presenceOfElementLocated(locator));
            wait.until(ExpectedConditions.textToBePresentInElement(elem, partialText));
            return elem;
        }

        public MyExpectedConditions condition() {
            return new MyExpectedConditions();
        }

        public void until(ExpectedCondition condition) {
            wait.until(condition);
        }
    }
}
