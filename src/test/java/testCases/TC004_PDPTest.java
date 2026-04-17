package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.ProductDisplayPage;
import pageObjects.SearchResults;
import testBase.BaseClass;

public class TC004_PDPTest extends BaseClass{
	
	@Test(groups={"Regression","Master"})
	public void verifyPDP() {
		try {
			logger.info("***** Starting TC004_PDPTest *****");
			
			String product=p.getProperty("searchProdName");
			
			// Login
			HomePage hp= new HomePage(driver);
			hp.clickMyAcc();
			hp.clickLogin();
			logger.info("Navigated to login page");
			
			LoginPage lp= new LoginPage(driver);
			lp.setEmail(p.getProperty("email"));
			lp.setPassword(p.getProperty("password"));
			lp.clickLoginbtn();
			logger.info("Logged in as: " + p.getProperty("email"));
			Thread.sleep(2000);
			
			// Search for product
			logger.info("Searching for product: " + product);
			hp.enterProduct(product);
			hp.clickSearch();
			
			// Verify and click product in results
			SearchResults sr= new SearchResults(driver);
			logger.info("Total results found: " + sr.getResultCount());
			
			boolean status= sr.isProductDisplayed(product);
			if (status) {
				logger.info("Product found: " + product);
				sr.clickProduct(product);
			} else {
				logger.warn("Product not found: " + product);
				Assert.fail("Product not found in search results: " + product);
			}
			
			// Verify product display page loaded
			ProductDisplayPage pdp= new ProductDisplayPage(driver);
			Assert.assertTrue(pdp.isTitleDisplayed(product),
				"Wrong product page loaded - expected: " + product+ 
				" but found: "+ pdp.getPageTitle());
			logger.info("Product page loaded: " + pdp.getPageTitle());
			
			// Add to cart
			logger.info("Adding product to cart");
			pdp.addToCart();
			
			// Verify cart success message
			String cartMsg= pdp.getAlertMessage();
			logger.info("Cart message: " + cartMsg); 
			Assert.assertTrue(cartMsg.contains("Success"),
				"Add to cart failed - message: " + cartMsg);
			Assert.assertTrue(cartMsg.contains(product), 
				"Wrong product added to cart - message: " + cartMsg);
			logger.info("Product added to cart - message: ");
			
			logger.info("***** TC004_PDPTest PASSED *****");
			
		} catch (Exception e) {
			logger.error("TC004_PDPTest FAILED: "+ e.getMessage());
			Assert.fail(e.getMessage());
		}	
		
	}

}
