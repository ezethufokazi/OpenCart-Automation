package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CartPage;
import pageObjects.CheckoutPage;
import pageObjects.ProductDisplayPage;
import pageObjects.SearchResults;
import testBase.BaseClass;

public class TC007_GuestCheckoutTest extends BaseClass{
	
	@Test(groups= {"Regression", "Master"})
	public void verifyGuestCheckout() throws InterruptedException {
		try {
		logger.info("***** Starting TC007_GuestCheckoutTest ******");
		
		String product= p.getProperty("searchProdName");
		
		// Navigate directly to search results
		logger.info("Navigating to search results for: " + product);
		//http://localhost/opencart/
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
		
		// Select guest checkout
		CheckoutPage cop= new CheckoutPage(driver);
		cop.selectGuestCheckout();
		logger.info("Selected guest checkout");
		
		// Enter personal details
		String firstName= randomString();
		String lastName= randomString();
		String email= randomString() + "@gmail.com";
		cop.setPersonalDetails(firstName, lastName, email);
		logger.info("Personal details entered for: " + email);
		
		// Enter shipping address
		String addressline1= p.getProperty("address.line1");
		String city= p.getProperty("address.city");
		String postcode= p.getProperty("address.postcode");
		String country= p.getProperty("address.country");
		String region= p.getProperty("address.region");
		cop.setShippingAddress(addressline1, city, postcode, country, region);
		cop.clickRegContinue();
		logger.info("Shipping address entered");
		
		// Verify information is saved
		boolean infoSaved= cop.getInformationSavedMsg();
		Assert.assertTrue(infoSaved, "Guest information was not saved successfully");
		logger.info("Guest information saved successfully");
		
		// Select shipping and payment method
		cop.selectShippingMethod();
		logger.info("Shipping method selected");
		
		cop.selectPaymentMethod();
		logger.info("Payment method selected");
		
		// Verify total price
		boolean priceCorrect= cop.verifyTotalPrice();
		Assert.assertTrue(priceCorrect, "Total price calculation incorrect - shipping not applied correclty");
		logger.info("Total price verified successfully");
		
		// Confirm order
		cop.confirmOrder();
		
		// Verify order placed
		boolean orderPlaced= cop.isOrderPlaced();
		Assert.assertTrue(orderPlaced, "Order was not placed successfully");
		logger.info("Order placed successfully");
		
		logger.info("***** TC007_GuestCheckoutTest PASSED *****");
		
		} catch (Exception e) {
			logger.error("TC007_GuestCheckoutTest FAILED: " + e.getMessage());
			Assert.fail(e.getMessage());
		}		
	
		}
	
}
