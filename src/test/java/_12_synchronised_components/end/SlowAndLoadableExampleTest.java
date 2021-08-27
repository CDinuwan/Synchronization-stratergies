package _12_synchronised_components.end;

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
import org.openqa.selenium.support.ui.SlowLoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Clock;

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
        startButton.get();
        startButton.click();

        ButtonComponent buttonOne = new ButtonComponent(driver, By.id("button01"));
        buttonOne.get();
        buttonOne.click();

        ButtonComponent buttonTwo = new ButtonComponent(driver, By.id("button02"));
        buttonTwo.get();
        buttonTwo.click();

        ButtonComponent buttonThree = new ButtonComponent(driver, By.id("button03"));
        buttonThree.get();
        buttonThree.click();

        Assertions.assertEquals("All Buttons Clicked",
                driver.findElement(By.id("buttonmessage")).getText());
    }

    private class ButtonComponent extends SlowLoadableComponent<ButtonComponent> {
        private final By locator;
        private final WebDriver myDriver;

        public ButtonComponent(WebDriver myDriver, final By elementLocator) {
            super(Clock.systemDefaultZone(), 10);
            this.myDriver = myDriver;
            this.locator = elementLocator;
        }


        public void click() {
            this.myDriver.findElement(this.locator).click();
        }

        @Override
        protected void load() {

        }

        @Override
        protected void isLoaded() throws Error {
            try {
                WebElement elem = this.myDriver.findElement(this.locator);
                if (elem.isDisplayed() && elem.isEnabled()) {
                    return;
                } else {
                    throw new Error(String.format("Button %s not ready", this.locator));
                }
            }catch(Exception e){
                throw new Error(e.getMessage(), e);
            }
        }
    }

    @AfterEach
    public void closeDriver(){
        driver.close();
    }

}
