package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import jdk.jfr.Description;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import org.testng.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class InvalidLogin {

    public static WebDriver driver;

    @BeforeSuite
    public static void setUp () {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
        driver.get("https://katalon-demo-cura.herokuapp.com/");
        driver.manage().window().maximize();


    }
    @Test
    @Description("Verify that with incorrect username and password login is unsuccessful")
    public static  void loginUsingInvalidUsername () {
        WebElement menu = driver.findElement(By.id("menu-toggle"));
        menu.click();
        WebElement loginMenu = driver.findElement(By.linkText("Login"));
        loginMenu.click();
        WebElement username = driver.findElement(By.id("txt-username"));
        username.sendKeys("12345");
        WebElement password = driver.findElement(By.cssSelector("input#txt-password"));
        password.sendKeys("ThisIsNotAPassword");
        WebElement loginButton = driver.findElement(By.cssSelector("button#btn-login"));
        loginButton.click();
        WebElement errorMessage1 = driver.findElement(By.className("lead"));
        Assert.assertTrue(errorMessage1.isDisplayed(), "Error message1 is not displayed");
        Assert.assertEquals("Please login to make appointment.", errorMessage1.getText());

    }

    public static void main(String[] args) {
        setUp();
        loginUsingInvalidUsername();
    }

    @AfterSuite
    public void tearDown () {
        driver.quit();
    }
}
