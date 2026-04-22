package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CartPage;
import pageObjects.CheckoutPage;
import pageObjects.ProductDisplayPage;
import pageObjects.SearchResults;
import testBase.BaseClass;

public class TC008_RegisterCheckout extends BaseClass{
	
	@Test(groups= {"Regression","Master"})
	public void verifyRegisterAndCheckout() throws InterruptedException {
		try {
		logger.info("***** Starting TC008_RegisterCheckout *****");
		
		String product= p.getProperty("searchProdName");
		
		// Navigate directly to search results
		logger.info("Navigating to search results for: " + product);
		driver.get(p.getProperty("appURL") + "index.php?route=product/search&search="+ product);
		
		// Click on product
		SearchResults sr= new SearchResults(driver);
		logger.info("Total results found: " + sr.getResultCount());
		sr.clickProduct(product);
		logger.info("Clicked on product: " + product);
		
		// Add to cart
		ProductDisplayPage pdp= new ProductDisplayPage(driver);
		Assert.assertTrue(pdp.isTitleDisplayed(product),
			"Wrong product page loaded");
		logger.info("Adding product to cart");
		pdp.addToCart();
		
		String cartMsg=	pdp.getAlertMessage();
		logger.info("Cart message: " + cartMsg);
		Assert.assertTrue(cartMsg.contains("Success"),
			"Add to cart failed - message: " + cartMsg);
		Assert.assertTrue(cartMsg.contains(product), 
			"Wrong product added to cart - message: " + cartMsg);
		logger.info("Product successfully added to cart");
		
		// Navigate to cart and checkout
		pdp.goToCart();
		CartPage cp= new CartPage(driver);
		cp.checkout();
		logger.info("Navigated to checkout page");
		
		// Select register account
		CheckoutPage cop= new CheckoutPage(driver);
		cop.selectRegisterCheckout();
		logger.info("Selected register account");
		
		// Enter personal details
		String firstName= randomString();
		String lastName= randomString();
		String email= randomString() + "@gmail.com";
		cop.setPersonalDetails(firstName, lastName, email);
		logger.info("Personal details entered for: " + email);
		
		// Enter shipping address and password
		String addressline1= p.getProperty("address.line1");
		String city= p.getProperty("address.city");
		String postcode= p.getProperty("address.postcode");
		String country= p.getProperty("address.country");
		String region= p.getProperty("address.region");
		String password=randomAlphaNumeric();
		cop.setShippingAddress(addressline1,city, postcode, country, region);
		cop.setPassword(password);
		cop.setPrivacyPolicy();
		cop.clickRegContinue();
		logger.info("Registration details submitted");
		
		// Verify information is saved
		boolean infoSaved= cop.getInformationSavedMsg();
		Assert.assertTrue(infoSaved, "Registration information was not saved successfully");
        logger.info("Registration information saved successfully");
		
        // Select shipping and payment method
		cop.selectShippingMethod();
		logger.info("Shipping method selected");
		
		cop.selectPaymentMethod();
		logger.info("Payment method selected");
		
		// Verify total price calculation
		boolean priceCorrect = cop.verifyTotalPrice();
		Assert.assertTrue(priceCorrect, "Total price calculation incorrect - shipping not applied correclty");
		logger.info("Total price verified successfully");

		// Confirm order
		cop.confirmOrder();
		logger.info("Order confirmed");

		// Verify order placed
		boolean orderPlaced = cop.isOrderPlaced();
		Assert.assertTrue(orderPlaced, "Order was not placed successfully");
		logger.info("Order placed successfully");

		logger.info("***** TC008_RegisterCheckout PASSED *****");

	} catch (Exception e) {
		logger.error("TC008_RegisterCheckout FAILED: " + e.getMessage());
		Assert.fail(e.getMessage());
	}
		
	}
	
	

}
