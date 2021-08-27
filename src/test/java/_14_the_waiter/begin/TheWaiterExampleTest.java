package _14_the_waiter.begin;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;


public class TheWaiterExampleTest {


    WebDriver driver;

    @BeforeAll
    public static void setupDriver(){
        WebDriverManager.chromedriver().setup();
    }

    private class ButtonPage {

        @FindBy(how = How.ID, using = "easy00")
        public WebElement startButton;

        @FindBy(how = How.ID, using = "easy01")
        public WebElement button01;

        @FindBy(how = How.ID, using = "easy02")
        public WebElement button02;

        @FindBy(how = How.ID, using = "easy03")
        public WebElement button03;

        @FindBy(how = How.ID, using = "easybuttonmessage")
        public WebElement buttonMessage;

        public ButtonPage(WebDriver driver){
            PageFactory.initElements(driver, this);
        }
    }

    @Test
    public void canClickOnButtons(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/buttons.html");

        ButtonPage page = new ButtonPage(driver);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        page.startButton.click();
        page.button01.click();
        page.button02.click();
        page.button03.click();

        Assertions.assertEquals("All Buttons Clicked",
                page. buttonMessage.getText());
    }

    @AfterEach
    public void closeDriver(){
        driver.close();
    }


}
