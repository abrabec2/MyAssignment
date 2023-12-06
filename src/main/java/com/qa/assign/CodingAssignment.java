package com.qa.assign;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class CodingAssignment {
    public static WebDriver driver;
    public static final String EMPTYCART_MESSAGE = """
                Your cart is empty.
                Log in to see items you may have added or saved during a previous visit, or continue shopping.
                Continue ShoppingLogin
                Want Free Shipping on Your Next Order?
                Our monthly subscription provides free shipping, priority processing, and much more! Learn More""";

    public static void main (String[] args) {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
        driver.get("https://www.webstaurantstore.com/");
        driver.manage().window().maximize();
        String title = driver.getTitle();
        Assert.assertEquals(title, "WebstaurantStore: Restaurant Supplies & Foodservice Equipment");

        //perform search for stainless work table
        driver.findElement(By.id("searchval")).sendKeys("stainless work table");
        driver.findElement(By.xpath("  //*[@id=\"searchForm\"]/div/button")).click();

        List<WebElement> results = driver.findElements(By.id("product_listing"));

        // Loop through the search results
        for (WebElement result : results) {
            String resultText = result.getText();
            System.out.println(resultText);

            //check if Table present in each product name
            if ((!resultText.contains("Table"))) {
                System.out.println("Product is missing 'Table' in title");
            }
        }//for

        //Adding last item to cart
        WebElement lastItem = results.get(results.size() - 1);
        WebElement addToCartButton = lastItem.findElement(By.name("addToCartButton"));
        addToCartButton.click();

        //Verify addtoCart operation
        WebElement notification = driver.findElement(By.className("notification__heading"));

        //Check notification after adding item to cart
        Assert.assertTrue(notification.isDisplayed(), "Notification is not displayed");

        // Check the text of the notification
        Assert.assertEquals("1 item added to your cart", notification.getText());
        notification.click();
        // Navigate to view item page
        WebElement viewItem = driver.findElement(By.xpath("//a[@href='/cart/']"));
        viewItem.click();

        // Empty Cart Operation
        WebElement emptyCartButton = driver.findElement(By.className("emptyCartButton"));
        emptyCartButton.click();

        // Verify empty cart operation
        WebElement alertBox = driver.findElement(By.id("empty-cart-body"));

        Assert.assertTrue(alertBox.isDisplayed(), "AlertBox is not displayed");

        // Check the text of the alertBox
        Assert.assertEquals("Are you sure you want to empty your cart?", alertBox.getText());

        //Click emptycart button in the alertbox
        WebElement emptyCartIcon = driver.findElement(By.xpath("//*[@id=\"td\"]/div[10]/div/div/div/footer/button[1]"));
        emptyCartIcon.click();

        //Verify emptycart page
        WebElement emptyCartPage = driver.findElement(By.className("cartEmpty"));

        Assert.assertTrue(emptyCartPage.isDisplayed(), "EmptyCartPage is not displayed");

        // Check the text of the empty cart page to verify
        Assert.assertEquals(EMPTYCART_MESSAGE, emptyCartPage.getText());
        try {
            Thread.sleep(2000);
        }
            catch(InterruptedException e) {
                System.out.println("thread interrupted");

            }

        //close the browser
        driver.quit();

    }
}
