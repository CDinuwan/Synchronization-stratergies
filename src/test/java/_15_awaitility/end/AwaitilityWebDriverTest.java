package _15_awaitility.end;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.Callable;

import static org.awaitility.Awaitility.await;

public class AwaitilityWebDriverTest {

    WebDriver driver;

    @BeforeAll
    public static void setupDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void canClickOnSecondButtonsWithComponentAndNoWebDriverWait(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/buttons.html");

        await().ignoreException(NoSuchElementException.class).
                until(()-> driver.findElement(By.id("button00")).isEnabled());
        driver.findElement(By.id("button00")).click();

//        ButtonComponent startButton = new ButtonComponent(driver, By.id("button00"));
//        startButton.waitTillReady();
//        startButton.click();

        ButtonComponent buttonOne = new ButtonComponent(driver, By.id("button01"));
        buttonOne.waitTillReady();
        buttonOne.click();

        ButtonComponent buttonTwo = new ButtonComponent(driver, By.id("button02"));
        buttonTwo.waitTillReady();
        buttonTwo.click();

        ButtonComponent buttonThree = new ButtonComponent(driver, By.id("button03"));
        buttonThree.waitTillReady();
        buttonThree.click();

        Assertions.assertEquals("All Buttons Clicked",
                driver.findElement(By.id("buttonmessage")).getText());
    }

    @AfterEach
    public void closeDriver(){
        driver.close();
    }

    private class ButtonComponent {
        private final By locator;
        private final WebDriver myDriver;
        private WebElement elem;

        public ButtonComponent(WebDriver myDriver, final By elementLocator) {
            this.myDriver = myDriver;
            this.locator = elementLocator;
        }

        public void waitTillReady() {
            await().ignoreException(NoSuchElementException.class).
                    until(isClickable());
        }

        private Callable<Boolean> isClickable() {
            elem = this.myDriver.findElement(locator);
            return () -> elem.isDisplayed() && elem.isEnabled();
        }

        public void click() {
            elem.click();
        }
    }

}
