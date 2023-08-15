package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.SearchResults;
import testBase.BaseClass;

public class TC003_SearchTest extends BaseClass {

    @Test(groups = {"Sanity", "Regression", "Master"})
    public void verify_search() {
        try {
            logger.info("**** Starting TC003_SearchTest ****");

            String product = p.getProperty("searchProdName");

            // Search for product from home page
            HomePage hp = new HomePage(driver);
            logger.info("Searching for product: " + product);
            hp.enterProduct(product);
            hp.clickSearch();

            // Verify search results
            SearchResults sr = new SearchResults(driver);
            logger.info("Total results found: " + sr.getResultCount());

            boolean status = sr.isProductDisplayed(product);

            if(status) {
                logger.info("Product found in results: " + product);
                Assert.assertTrue(status, "Product not displayed in results");

                // Click on product
                logger.info("Clicking on product: " + product);
                sr.clickProduct(product);
                logger.info("Successfully navigated to product page");

            } else {
                logger.warn("Product not found: " + product);
                logger.warn("No results message: " + sr.getNoResultMsg());
                Assert.fail("Product not found in search results: " + product);
            }

            logger.info("**** TC003_SearchTest PASSED ****");

        } catch(Exception e) {
            logger.error("TC003_SearchTest FAILED: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }
}