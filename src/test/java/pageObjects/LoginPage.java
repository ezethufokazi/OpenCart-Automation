package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage{
	
	public LoginPage(WebDriver driver) {
		super(driver);
		// wait for login page to load
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOf(txtEmailAddress));
}

// ===== Locators =====
@FindBy(xpath="//input[@id='input-email']")
WebElement txtEmailAddress;

@FindBy(xpath="//input[@id='input-password']") 
WebElement txtPassword;

@FindBy(xpath="//button[normalize-space()='Login']")
WebElement btnLogin;

// ===== Action Methods =====
public void setEmail(String email) {
	txtEmailAddress.clear();
	txtEmailAddress.sendKeys(email);
}

public void setPassword(String password) {
	txtPassword.clear();
	txtPassword.sendKeys(password);
}

public void clickLoginbtn() {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	wait.until(ExpectedConditions.elementToBeClickable(btnLogin));
	btnLogin.click();
}










}