package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CartPage;
import pageObjects.CheckoutPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.ProductDisplayPage;
import pageObjects.SearchResults;
import testBase.BaseClass;

public class TC009_LoggedInUserCheckoutExistingAddress extends BaseClass{
	
	@Test(groups= {"Regression", "Master"})
	public void verifyLoggedInUserCheckout() throws InterruptedException {
		try {
			logger.info("***** Starting TC009_LoggedInUserCheckoutExistingAddress *****");
			
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
			Thread.sleep(1000);
			
			// Search for product
			logger.info("Searching for product: " + product);
			hp.enterProduct(product);
			hp.clickSearch();
			
			// Verify and click product in results
			SearchResults sr= new SearchResults(driver);
			logger.info("Total results found: " + sr.getResultCount());
			
			boolean searchStatus= sr.isProductDisplayed(product);
			Assert.assertTrue(searchStatus, "Product not found in search results: " + product);
			sr.clickProduct(product);
			logger.info("Clicked on product: " + product);
			
			// Verify product page and add to cart
			ProductDisplayPage pdp= new ProductDisplayPage(driver);
			Assert.assertTrue(pdp.isTitleDisplayed(product),
				"Wrong product page loaded - expected: " + product+ 
				" but found: "+ pdp.getPageTitle());
			logger.info("Product page loaded: " + pdp.getPageTitle());
			
			pdp.addToCart();
			String cartMsg= pdp.getAlertMessage(); 
			Assert.assertTrue(cartMsg.contains("Success"),
				"Add to cart failed - message: " + cartMsg);
			logger.info("Product added to cart successfully ");
			
			// Navigate to cart and checkout
			pdp.goToCart();
			CartPage cp= new CartPage(driver);
			cp.checkout();
			logger.info("Navigated to checkout page");
			
			// Select address option
			CheckoutPage cop= new CheckoutPage(driver);
			
			boolean existingAddress= cop.isExistingAddressDisplayed();
			if (existingAddress) {
			cop.selectExistingAddress();
			cop.selectAddress();
			logger.info("Shipping address selected");
			} else {
				
				// Fill in shipping details
				logger.info("Entering shipping address");
				String firstName= p.getProperty("firstName");
				String lastName= p.getProperty("lastName");
				String addressline1= p.getProperty("address.line1");
				String city= p.getProperty("address.city");
				String postcode= p.getProperty("address.postcode");
				String country= p.getProperty("address.country");
				String region= p.getProperty("address.region");
				cop.setLoggedInShippingAddress(firstName, lastName, addressline1, 
						city, postcode, country, region);
				cop.clickContinue();
				logger.info("Shipping address submitted");
				
				// Verify information saved
				boolean infoSaved= cop.getInformationSavedMsg();
				Assert.assertTrue(infoSaved, "Shipping information was not saved successfully");
				logger.info("Shipping information saved successfully");
			}
			
			
			// Select shipping and payment method
			cop.selectShippingMethod();
			logger.info("Shipping method selected");
			
			cop.selectPaymentMethod();
			logger.info("Payment method selected");
			
			// Verify total price
			boolean priceCorrect= cop.verifyTotalPrice();
			Assert.assertTrue(priceCorrect, "Total price incorrect - shipping not applied correctly");
			logger.info("Total price verified successfully");
		
			// Confirm order
			cop.confirmOrder();
			logger.info("Order confirmed");
			
			// Verify order placed
			boolean orderPlaced= cop.isOrderPlaced();
			Assert.assertTrue(orderPlaced, "Order was not placed successfully");
            logger.info("Order placed successfully");

            logger.info("***** TC009_LoggedInUserCheckoutExistingAddress PASSED *****");
			
		}  catch (Exception e) {
			 logger.error("TC009_LoggedInUserCheckoutExistingAddress FAILED: " + e.getMessage());
	         Assert.fail(e.getMessage());		
		}				
	}
}
