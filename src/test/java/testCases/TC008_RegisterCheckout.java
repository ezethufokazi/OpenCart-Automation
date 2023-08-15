package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.CartPage;
import pageObjects.CheckoutPage;
import pageObjects.ProductDisplayPage;
import pageObjects.SearchResults;
import testBase.BaseClass;

public class TC008_RegisterCheckout extends BaseClass {

    @Test(groups = {"Regression", "Master"})
    public void verifyRegisterAndCheckout() throws InterruptedException {
        try {
            logger.info("***** Starting TC008_RegisterCheckout *****");

            String product = p.getProperty("searchProdName");

            // Navigate directly to search results
            driver.get(p.getProperty("appURL")
                + "index.php?route=product/search&search=" + product);

            // Click on product
            SearchResults sr = new SearchResults(driver);
            logger.info("Total results found: " + sr.getResultCount());
            sr.clickProduct(product);

            // Add to cart
            ProductDisplayPage pdp = new ProductDisplayPage(driver);
            Assert.assertTrue(pdp.isTitleDisplayed(product),
                "Wrong product page loaded");
            pdp.addToCart();
            Assert.assertTrue(pdp.getAlertMessage().contains("Success"),
                "Add to cart failed");
            logger.info("Product added to cart successfully");

            // Navigate to cart and checkout
            pdp.goToCart();
            CartPage cp = new CartPage(driver);
            cp.checkout();

            // Select register checkout
            CheckoutPage cop = new CheckoutPage(driver);
            cop.selectRegisterCheckout();

            // Enter personal details
            String firstName = randomString();
            String lastName  = randomString();
            String email     = randomString() + "@gmail.com";
            String password  = randomAlphaNumeric();
            cop.setPersonalDetails(firstName, lastName, email);
            logger.info("Personal details entered for: " + email);

            // Enter shipping address and password
            cop.setShippingAddress(
                p.getProperty("address.line1"),
                p.getProperty("address.city"),
                p.getProperty("address.postcode"),
                p.getProperty("address.country"),
                p.getProperty("address.region"));
            cop.setPassword(password);
            cop.setPrivacyPolicy();
            cop.clickRegContinue();
            logger.info("Registration details submitted");

            Assert.assertTrue(cop.getInformationSavedMsg(),
                "Registration information was not saved successfully");
            logger.info("Registration information saved successfully");

            // Shipping and payment
            cop.selectShippingMethod();
            cop.selectPaymentMethod();

            Assert.assertTrue(cop.verifyTotalPrice(),
                "Total price incorrect");
            cop.confirmOrder();
            Assert.assertTrue(cop.isOrderPlaced(),
                "Order was not placed successfully");
            logger.info("Order placed successfully");

            logger.info("***** TC008_RegisterCheckout PASSED *****");

        } catch(Exception e) {
            logger.error("TC008_RegisterCheckout FAILED: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }
}