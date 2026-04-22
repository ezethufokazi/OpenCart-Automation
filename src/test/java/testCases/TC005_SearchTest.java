package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.SearchResults;
import testBase.BaseClass;

public class TC005_SearchTest extends BaseClass {
	
	@Test(groups= {"Sanity", "Regression", "Master"})
	public void verify_search() {
		try {
		logger.info("**** Starting TC005_SearchTest ****");
		
		String product=p.getProperty("searchProdName");
		
		// Search for product
		HomePage hp= new HomePage(driver);
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
		
		logger.info("**** TC005_SearchTest PASSED ****");
	
		} catch (Exception e) {
			logger.error("TC005_SearchTest FAILED: "+ e.getMessage());
			Assert.fail(e.getMessage());
		}
		
		
	}
}
