package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccount;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	
	@Test(groups={"Smoke","Sanity", "Master"})
	public void verifyLogin() {
		try {
			logger.info("**** Starting TC002_LoginTest ****");
			
			// Navigate to login page
			HomePage hp= new HomePage(driver);
			
			hp.clickLogin();
			logger.info("Navigated to login page");
			
			// Enter login credentials
			LoginPage lp= new LoginPage(driver);
			lp.setEmail(p.getProperty("email"));
			lp.setPassword(p.getProperty("password"));
			logger.info("Entered credentials for: " + p.getProperty("email"));
			
			lp.clickLoginbtn();
			logger.info("Login button clicked - current URL: " + driver.getCurrentUrl());
			
			// Verify successful login
			MyAccount mc= new MyAccount(driver);
			boolean targetPage= mc.myAccExists();
			Assert.assertTrue(targetPage, 
				"Login failed - My Account page not displayed");
			
			logger.info("**** TC002_LoginTest PASSED ****");
			
		} catch (Exception e) {
				logger.error("TC002_LoginTest FAILED: " + e.getMessage());
				Assert.fail(e.getMessage());
	
			}
			
			
	}
		
}

