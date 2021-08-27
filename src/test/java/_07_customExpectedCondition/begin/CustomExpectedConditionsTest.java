package _07_customExpectedCondition.begin;

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

public class CustomExpectedConditionsTest {

    WebDriver driver;

    @BeforeAll
    public static void setupDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void expectedConditionsClickableLink(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/collapseable.html");

        final WebElement section = driver.findElement(By.cssSelector("section.condense"));
        section.click();

        final By linkToClick = By.cssSelector("a#aboutlink");
        new WebDriverWait(driver, 10).
                until(ExpectedConditions.elementToBeClickable(linkToClick));

        driver.findElement(linkToClick).click();

        Assertions.assertTrue(driver.getCurrentUrl().contains("about.html"));
    }

    @AfterEach
    public void closeDriver(){
        driver.close();
    }

}
