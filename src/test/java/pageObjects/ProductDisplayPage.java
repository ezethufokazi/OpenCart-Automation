package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProductDisplayPage extends BasePage {

    public ProductDisplayPage(WebDriver driver) {
        super(driver);
        // wait for product page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(headingPageTitle));
    }

    // ===== Locators =====
    @FindBy(xpath="//div[@id='content']//h1")
    WebElement headingPageTitle; // dynamic - works for any product

    @FindBy(css="#button-cart")
    WebElement btnAddToCart;

    @FindBy(xpath="//button[@aria-label='Add to Wish List']")
    WebElement btnAddToWishlist;

    @FindBy(xpath="//button[@aria-label='Compare this Product']")
    WebElement btnAddToCompareList;

    @FindBy(xpath="//div[@class='alert alert-success alert-dismissible']")
    WebElement alertSuccessMsg;

    @FindBy(xpath="//span[normalize-space()='Shopping Cart']")
    WebElement lnkShoppingCart;

    // ===== Action Methods =====

    // returns true if product title contains expected name
    public boolean isTitleDisplayed(String productName) {
        try {
            return headingPageTitle.getText().contains(productName);
        } catch(Exception e) {
            return false;
        }
    }

    // returns product title text
    public String getPageTitle() {
        return headingPageTitle.getText();
    }

    // adds product to cart
    public void addToCart() {
        btnAddToCart.click();
    }

    // returns success/error message after cart action
    public String getAlertMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(alertSuccessMsg));
            return alertSuccessMsg.getText();
        } catch(Exception e) {
            return "";
        }
    }

    // adds product to wishlist
    public void addToWishlist() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Hover over the product image first to reveal buttons
        Actions actions = new Actions(driver);
        actions.moveToElement(btnAddToWishlist).perform();

        // Wait for button to be visible then click
        wait.until(ExpectedConditions.elementToBeClickable(btnAddToWishlist));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", btnAddToWishlist);
    }

    // adds product to compare list
    public void addToCompareList() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        Actions actions = new Actions(driver);
        actions.moveToElement(btnAddToCompareList).perform();

        wait.until(ExpectedConditions.elementToBeClickable(btnAddToCompareList));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", btnAddToCompareList);
    }

    // navigates to shopping cart page
    public void goToCart() {
        lnkShoppingCart.click();
    }
}