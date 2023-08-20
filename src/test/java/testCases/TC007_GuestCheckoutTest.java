package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CartPage;
import pageObjects.CheckoutPage;
import pageObjects.ProductDisplayPage;
import pageObjects.SearchResults;
import testBase.BaseClass;

public class TC007_GuestCheckoutTest extends BaseClass {

    @Test(groups = {"Regression", "Master"})
    public void verifyGuestCheckout() throws InterruptedException {
        try {
            logger.info("***** Starting TC007_GuestCheckoutTest *****");

            String product = p.getProperty("searchProdName");

            // Navigate directly to search results
            driver.get(p.getProperty("appURL")
                + "index.php?route=product/search&search=" + product);
            logger.info("Searching for: " + product);

            // Click on product
            SearchResults sr = new SearchResults(driver);
            logger.info("Total results found: " + sr.getResultCount());
            sr.clickProduct(product);

            // Add to cart
            ProductDisplayPage pdp = new ProductDisplayPage(driver);
            Assert.assertTrue(pdp.isTitleDisplayed(product),
                "Wrong product page loaded");
            pdp.addToCart();

            String cartMsg = pdp.getAlertMessage();
            Assert.assertTrue(cartMsg.contains("Success"),
                "Add to cart failed - message: " + cartMsg);
            logger.info("Product added to cart successfully");

            // Navigate to cart and checkout
            pdp.goToCart();
            CartPage cp = new CartPage(driver);
            cp.checkout();
            logger.info("Navigated to checkout page");

            // Select guest checkout
            CheckoutPage cop = new CheckoutPage(driver);
            cop.selectGuestCheckout();
            logger.info("Selected guest checkout");

            // Enter personal details
            String firstName = randomString();
            String lastName  = randomString();
            String email     = randomString() + "@gmail.com";
            cop.setPersonalDetails(firstName, lastName, email);
            logger.info("Personal details entered for: " + email);

            // Enter shipping address
            cop.setShippingAddress(
                p.getProperty("address.line1"),
                p.getProperty("address.city"),
                p.getProperty("address.postcode"),
                p.getProperty("address.country"),
                p.getProperty("address.region"));
            cop.clickRegContinue();
            logger.info("Shipping address entered");

            // Verify information saved
            boolean infoSaved = cop.getInformationSavedMsg();
            Assert.assertTrue(infoSaved,
                "Guest information was not saved successfully");
            logger.info("Guest information saved successfully");

            // Select shipping and payment method
            cop.selectShippingMethod();
            cop.selectPaymentMethod();

            // Verify total price
            Assert.assertTrue(cop.verifyTotalPrice(),
                "Total price incorrect - shipping not applied correctly");
            logger.info("Total price verified successfully");

            // Confirm order
            cop.confirmOrder();
            Assert.assertTrue(cop.isOrderPlaced(),
                "Order was not placed successfully");
            logger.info("Order placed successfully");

            logger.info("***** TC007_GuestCheckoutTest PASSED *****");

        } catch(Exception e) {
            logger.error("TC007_GuestCheckoutTest FAILED: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }
}