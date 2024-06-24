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



public class Login {

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
        @Description("Verify that with correct username and password login is successful")
        public static void loginUsingValidUsername () {

            WebElement menu = driver.findElement(By.id("menu-toggle"));
            menu.click();
            WebElement loginMenu = driver.findElement(By.linkText("Login"));
            loginMenu.click();
            WebElement username = driver.findElement(By.id("txt-username"));
            username.sendKeys("John Doe");
            WebElement password = driver.findElement(By.cssSelector("input#txt-password"));
            password.sendKeys("ThisIsNotAPassword");
            WebElement loginButton = driver.findElement(By.cssSelector("button#btn-login"));
            loginButton.click();
        }




         public static void main(String[] args) {
          setUp();
          loginUsingValidUsername();

        }

        @AfterSuite
        public void tearDown () {
            driver.quit();
        }
    }
