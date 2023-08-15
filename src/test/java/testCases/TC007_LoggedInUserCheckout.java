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

public class TC007_LoggedInUserCheckout extends BaseClass {

    @Test(groups = {"Regression", "Master"})
    public void verifyLoggedInUserCheckout() throws InterruptedException {
        try {
            logger.info("***** Starting TC007_LoggedInUserCheckout *****");

            String product = p.getProperty("searchProdName");

            // Login
            HomePage hp = new HomePage(driver);
            hp.clickLogin();
            LoginPage lp = new LoginPage(driver);
            lp.setEmail(p.getProperty("email"));
            lp.setPassword(p.getProperty("password"));
            lp.clickLoginbtn();
            logger.info("Logged in as: " + p.getProperty("email"));

            // Search and click product
            hp.enterProduct(product);
            hp.clickSearch();

            SearchResults sr = new SearchResults(driver);
            Assert.assertTrue(sr.isProductDisplayed(product),
                "Product not found: " + product);
            sr.clickProduct(product);

            // Add to cart
            ProductDisplayPage pdp = new ProductDisplayPage(driver);
            pdp.addToCart();
            String cartMsg = pdp.getAlertMessage();
            Assert.assertTrue(cartMsg.contains("Success"),
                "Add to cart failed");
            logger.info("Product added to cart successfully");

            // Navigate to cart and checkout
            pdp.goToCart();
            CartPage cp = new CartPage(driver);
            cp.checkout();
            logger.info("Navigated to checkout page");

            // Handle existing or new address
            CheckoutPage cop = new CheckoutPage(driver);
            boolean existingAddress = cop.isExistingAddressDisplayed();
            if(existingAddress) {
                cop.selectExistingAddress();
                cop.selectAddress();
                cop.clickContinue();
            } else {
                cop.setLoggedInShippingAddress(
                    p.getProperty("firstName"),
                    p.getProperty("lastName"),
                    p.getProperty("address.line1"),
                    p.getProperty("address.city"),
                    p.getProperty("address.postcode"),
                    p.getProperty("address.country"),
                    p.getProperty("address.region"));
                cop.clickContinue();

                boolean infoSaved = cop.getInformationSavedMsg();
                Assert.assertTrue(infoSaved,
                    "Shipping information was not saved successfully");
                logger.info("Shipping information saved successfully");
            }

            // Shipping and payment
            cop.selectShippingMethod();
            cop.selectPaymentMethod();

            // Verify total and confirm
            Assert.assertTrue(cop.verifyTotalPrice(),
                "Total price incorrect");
            cop.confirmOrder();
            Assert.assertTrue(cop.isOrderPlaced(),
                "Order was not placed successfully");
            logger.info("Order placed successfully");

            logger.info("***** TC007_LoggedInUserCheckout PASSED *****");

        } catch(Exception e) {
            logger.error("TC007_LoggedInUserCheckout FAILED: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }
}