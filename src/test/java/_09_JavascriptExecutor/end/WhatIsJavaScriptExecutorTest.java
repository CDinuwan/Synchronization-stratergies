package _09_JavascriptExecutor.end;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
        JavascriptExecutor exec = (JavascriptExecutor) driver;
//        exec.executeScript("arguments[0].innerText=arguments[1];",
//                heading, "My New Heading Text");

        exec.executeAsyncScript(
                "window.setTimeout(function(arguments){"+
                        "arguments[0].innerText=arguments[1];"+
                        "arguments[arguments.length-1]();"+
                        "},5000, arguments);",
                heading, "My New Heading Text");

        Assertions.assertEquals("My New Heading Text",
                heading.getText());

    }

    @AfterEach
    public void closeDriver(){
        driver.close();
    }


}
