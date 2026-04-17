package testCases;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccRegPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccRegTest extends BaseClass{
	
	@Test(groups={"Smoke", "Regression", "Master"})
	public void verify_acc_reg() {
		try {
		logger.info("**** Starting TC001_AccRegTest ****");
		
		// Navigate to registration page
		HomePage hp= new HomePage(driver);
		hp.clickMyAcc();
		hp.clickRegister();;
		logger.info("Navigated to registration page");
		
		// Fill in registration form
		AccRegPage regpage= new AccRegPage(driver);
		logger.info("Providing customer details");
		
		String firstName= randomString().toUpperCase();
		String lastName= randomString().toUpperCase();
		String email= randomString() +"@gmail.com";
		String password= randomAlphaNumeric();
		
		regpage.setFirstName(firstName);
		regpage.setLastName(lastName);
		regpage.setEmail(email);
		regpage.setPassword(password);
	
		logger.info("Registered with email: " + email);
		
		// Accept privacy policy and submit
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		logger.info("Form submitted");
		
		// Validate confirmation message
		logger.info("Validating confirmation mesage");
		String msg=	regpage.getConfirmationMsg();		
		Assert.assertEquals(msg, "Your Account Has Been Created!", 
			"Account registration failed - confirmation message not displayed");
		
		logger.info("**** TC001_AccRegTest PASSED **** ");
		
	} catch(Exception e) {
		logger.error("TC001_AccRegTest FAILED: " + e.getMessage());
		Assert.fail(e.getMessage());
		}
		
		logger.info("**** Finished ****");
	}
	
}
