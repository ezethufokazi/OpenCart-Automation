package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CartPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.ProductDisplayPage;
import pageObjects.SearchResults;
import testBase.BaseClass;

public class TC006_VerifyCartPage extends BaseClass {
	@Test(groups={"Regression","Master"})
	public void verify_cart() throws InterruptedException {
		try {
			logger.info("***** Starting TC006_VerifyCartPage *****");
			
			String product=	p.getProperty("searchProdName");
			
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
			
			// Verify search results
			SearchResults sr= new SearchResults(driver);
			logger.info("Total results found: " + sr.getResultCount());
			
			Boolean searchStatus= sr.isProductDisplayed(product);
			Assert.assertTrue(searchStatus,
				"Product not found in search results: " + product);
			logger.info("Product found: " + product);
			
			sr.clickProduct(product);
			logger.info("Clicked on product: " + product);
			
			// Verify product display page
			ProductDisplayPage pdp= new ProductDisplayPage(driver);
			Assert.assertTrue(pdp.isTitleDisplayed(product),
				"Wrong product page - expected: " + product
				+ " but found: " + pdp.getPageTitle());
			logger.info("Product page loaded: " + pdp.getPageTitle());
			
			// Add to cart and verify
			logger.info("Adding product to cart");
			pdp.addToCart();
			
			String cartMsg=	pdp.getAlertMessage();
			logger.info("Cart message: " + cartMsg);
			Assert.assertTrue(cartMsg.contains("Success"),
				"Add to cart failed - message: " + cartMsg);
			Assert.assertTrue(cartMsg.contains(product), 
				"Wrong product added to cart - message: " + cartMsg);
			logger.info("Product successfully added to cart");
			
			// Navigate to cart
			logger.info("Navigating to shopping cart");
			pdp.goToCart();
			
			// Verify correct product in cart
			CartPage cp= new CartPage(driver);
			boolean productInCart= cp.verifyCorrectProduct(product);
			Assert.assertTrue(productInCart, 
				"Wrong product in cart - expected: " + product);
			logger.info("Correct product found in cart: " + product);
			
			// Get prices before update
			String unitPrice= cp.getUnitPrice();
			String totalBefore= cp.getTotalPrice();
			logger.info("Unit price: " + unitPrice);
			logger.info("Total before quantity update: " + totalBefore);
			
			// Update quantity and verify total changes
			logger.info("Updating quantity to 5");
			cp.updateQuantity("5");
			cp.clickUpdate();
		
			String totalAfter= cp.getTotalPrice();
			logger.info("Total after quantity update: "+ totalAfter);
			Assert.assertNotEquals(totalBefore, totalAfter,
					"Total did not change after quantity update");
			logger.info("Total updated successfully");
			
			// Remove product and verify cart is empty
			logger.info("Removing product from cart");
			cp.removeProduct();
			
			boolean cartEmpty= cp.isCartEmpty();
			Assert.assertTrue(cartEmpty,
					"Cart is not empty after product removal");
			logger.info("Cart is empty: "+ cp.getEmptyCartMessage());
			
			logger.info("***** TC006_VerifyCartPage PASSED *****");
		
			} catch (Exception e) {
				logger.error("TC006_VerifyCartPage FAILED: " + e.getMessage());
				Assert.fail(e.getMessage());
			}		
	}
}
