package pageObjects;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends BasePage{
	public CartPage(WebDriver driver) {
		super(driver);
		// wait for cart page to load
		   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	       wait.until(ExpectedConditions.urlContains("checkout"));	
	}
	
	// ===== Locators =====
	@FindBy(xpath="//td[@class='text-start text-wrap']//a")
	WebElement lnkproductName;
	
	@FindBy(xpath="//input[@name='quantity']")
	WebElement txtQuantity;
	
	@FindBy(xpath="//button[contains(@formaction,'cart.edit')]")
	WebElement btnUpdate;
	
	@FindBy(xpath="//a[contains(@href,'cart.remove')]")
	WebElement btnRemove;
	
	@FindBy(xpath="//div[@class='table-responsive']//tbody//tr[1]//td[4]")
	WebElement tdUnitPrice;
	
	@FindBy(xpath="//tfoot//tr[td/strong[text()='Total']]//td[2]")
	WebElement tdTotalPrice;
	
	@FindBy(xpath="//p[normalize-space()='Your shopping cart is empty!']") 
	WebElement msgEmptyCart;
	
	@FindBy(xpath="//a[text()='Checkout']")
	WebElement btnCheckout;
	
	
	// ===== Action Methods =====
	
	// returns true if product name in cart matches expected
	public boolean verifyCorrectProduct(String prodName) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    wait.until(ExpectedConditions.visibilityOf(lnkproductName));	
			return lnkproductName.getText().contains(prodName);
		} catch (Exception e) {
			return false;
		}
	}
	
	// returns unit price of product in cart
	public String getUnitPrice() {
		return tdUnitPrice.getText();
	}
	
	// returns total price from cart summary
	public String getTotalPrice() throws InterruptedException {
		Thread.sleep(5000);
		return tdTotalPrice.getText();
	}
	
	// clears quantity filed and enters new value
	public void updateQuantity(String quantity) {
		txtQuantity.clear();
		txtQuantity.sendKeys(quantity);
	}
	
	// clicks update button using JS to avoid overlay issue
	public void clickUpdate() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(btnUpdate));
		JavascriptExecutor js= (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", btnUpdate);
	}
	
	// removes product from cart
	public void removeProduct() {
		JavascriptExecutor js= (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", btnRemove);
	}
	
	// returns true if cart is empty
	public boolean isCartEmpty() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(msgEmptyCart));
			return msgEmptyCart.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
	
	// returns empty cart message text
	public String getEmptyCartMessage() {
		try {
			return msgEmptyCart.getText();
		} catch (Exception e) {
			return "";
		}
		
	}
	
	// scrolls to and clicks checkout button
	public void checkout() {
		JavascriptExecutor js= (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView({block: 'center'});",btnCheckout);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.elementToBeClickable(btnCheckout));
		btnCheckout.click();
	}
	

}
