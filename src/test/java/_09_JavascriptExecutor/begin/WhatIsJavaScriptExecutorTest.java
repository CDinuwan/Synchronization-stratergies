package _09_JavascriptExecutor.begin;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/*
    - driver can be cast to ((JavascriptExecutor) driver)
    - allowing arbitrary JavaScript to be executed on the page
    - synchronously exec.executeScript
    - and asynchronously exec.executeAsyncScript
 */
public class WhatIsJavaScriptExecutorTest {

    WebDriver driver;

    @BeforeAll
    public static void setupDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void canManipulatePageWithJavaScript(){

        driver = new ChromeDriver();

        driver.get("https://eviltester.github.io/synchole/collapseable.html");

        final WebElement heading = driver.findElement(
                        By.cssSelector("section.synchole > h2"));

        // cast the driver to JavascriptExecutor
        // use exec to change the inner text of the heading
        // assert on the changed Text

        Assertions.assertEquals("SyncHole",
                                heading.getText());

    }

    @AfterEach
    public void closeDriver(){
        driver.close();
    }


}
