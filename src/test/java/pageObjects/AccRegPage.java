package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccRegPage extends BasePage{
	
	public AccRegPage(WebDriver driver) {
		super(driver);
		
		// Wait for registration page to fully load
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.urlContains("register"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='input-firstname']")));
	}

	
// ====== Locators =======
@FindBy(xpath="//input[@id='input-firstname']")
WebElement txtFirstname;

@FindBy(xpath="//input[@id='input-lastname']")
WebElement txtLastname;

@FindBy(xpath="//input[@id='input-email']")
WebElement txtEmail;

@FindBy(xpath="//input[@id='input-password']")
WebElement txtPassword;

@FindBy(xpath="//input[@name='agree']") 
WebElement chkAgree;

@FindBy(xpath="//button[normalize-space()='Continue']")
WebElement btnContinue;

@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']")
WebElement msgConfirmation;


// ====== Action Methods ======
public void setFirstName(String fname) {
	JavascriptExecutor js= (JavascriptExecutor) driver;
	js.executeScript("arguments[0].scrollIntoView(true);", txtFirstname);
    txtFirstname.clear();
	txtFirstname.sendKeys(fname);
}
public void setLastName(String lname) {
	txtLastname.clear();
	txtLastname.sendKeys(lname);
}

public void setEmail(String email) {
	txtEmail.clear();
	txtEmail.sendKeys(email);
}

public void setPassword(String password) {
	txtPassword.clear();
	txtPassword.sendKeys(password);
}

public void setPrivacyPolicy() {
	if (!chkAgree.isSelected()) {
	chkAgree.click();
	}
}

public void clickContinue() {
	btnContinue.click();
}

public String getConfirmationMsg() {
	try {
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[normalize-space()='Your Account Has Been Created!']")));
	return msgConfirmation.getText();
	} catch (Exception e) {
		return (e.getMessage());
	}
}











}
