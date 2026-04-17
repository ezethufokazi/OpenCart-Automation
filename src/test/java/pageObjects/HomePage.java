package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage{
	
	public HomePage(WebDriver driver) {
		super(driver);
	}

// ===== Locators =====	
@FindBy(xpath="//span[normalize-space()='My Account']") 
WebElement lnkMyaccount;

@FindBy(xpath="//a[normalize-space()='Register']")
WebElement lnkRegister;

@FindBy(xpath="//a[@class='dropdown-item'][normalize-space()='Login']")
WebElement lnkLogin;

@FindBy(xpath="//input[@placeholder='Search']") 
WebElement txtSearch;

@FindBy(xpath="//button[@type='submit']") 
WebElement btnSearch;

// ===== Action Methods =====

// clicks My Account dropdown
public void clickMyAcc() throws InterruptedException {
	WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(20));
	wait.until(ExpectedConditions.elementToBeClickable(lnkMyaccount));
	JavascriptExecutor js= (JavascriptExecutor) driver;
	js.executeScript("arguments[0].click();", lnkMyaccount);
}

// navigates to registration page
public void clickRegister() {
	WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));
	wait.until(ExpectedConditions.elementToBeClickable(lnkRegister));
	lnkRegister.click();
}

// navigates directly to login page
public void clickLogin() {
	driver.get("http://host.docker.internal/opencart/index.php?route=account/login");
	
}

// enter product name in search box
public void enterProduct(String productName) {
	WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(20));
	wait.until(ExpectedConditions.elementToBeClickable(txtSearch));
	txtSearch.clear();
	txtSearch.sendKeys(productName);
}

// click search button
public void clickSearch() {
	btnSearch.click();
}












}
