package pageObjects;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAccount extends BasePage {
	
	public MyAccount(WebDriver driver) {
		super(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    wait.until(ExpectedConditions.urlContains("account"));
		
	}
	
	
	// ===== Locators =====
	@FindBy(xpath="//h1[normalize-space()='My Account']")
	WebElement headingMyAcc;
	
	@FindBy(xpath="//a[@class='list-group-item'][normalize-space()='Logout']") 
	WebElement lnkLogout;
	
	// ===== Action Methods =====
	
	// returns true if My Account heading is displayed
	public boolean myAccExists() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		    wait.until(ExpectedConditions.visibilityOf(headingMyAcc));
			return (headingMyAcc.isDisplayed());
		}
		catch(Exception e){
			return false;
		}
			
		}
	
	// returns My Account heading text for verification
	public String getMyAccHeading() {
		return headingMyAcc.getText();
	}
	
	public void clickLogout() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", lnkLogout);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.elementToBeClickable(lnkLogout));
	    lnkLogout.click();
	}
	
}
