package _12_synchronised_components.begin;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SlowAndLoadableExampleTest {

    WebDriver driver;

    @BeforeAll
    public static void setupDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void canClickOnSecondButtonsWithComponent(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/buttons.html");

        ButtonComponent startButton = new ButtonComponent(driver, By.id("button00"));
        startButton.waitTillReady();
        startButton.click();

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

    private class ButtonComponent {
        private final By locator;
        private final WebDriver myDriver;

        public ButtonComponent(WebDriver myDriver, final By elementLocator) {
            this.myDriver = myDriver;
            this.locator = elementLocator;
        }

        public void waitTillReady() {
            new WebDriverWait(this.myDriver,10).
                    until(
                            ExpectedConditions.elementToBeClickable(this.locator));
        }

        public void click() {
            this.myDriver.findElement(this.locator).click();
        }
    }

    @AfterEach
    public void closeDriver(){
        driver.close();
    }

}
