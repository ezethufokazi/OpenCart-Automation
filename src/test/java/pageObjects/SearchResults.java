package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResults extends BasePage {
	
	public SearchResults(WebDriver driver) {
		super(driver);
		// wait for search results to load
		   WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	       wait.until(ExpectedConditions.urlContains("search"));		
	}
	
	// ===== Locators =====
	@FindBy(xpath="//div[@class='product-thumb']//h4/a")
	List <WebElement> products;
	
	@FindBy(xpath="//p[contains(text(),'There is no product that matches the search criteria')]")
	WebElement msgNoResults;
	
	// ===== Action Methods =====
	
	// returns true if product with matching name exists in results
	public boolean isProductDisplayed(String productName) {
		for (WebElement product: products) {
			if(product.getText().equalsIgnoreCase(productName)) {
				return true;
			}	
		}
		return false;
	}
	
	// returns number of results found
	public int getResultCount() {
		return products.size();
	}
	
	// clicks on product matching the given name
	public void clickProduct(String ProductName) throws InterruptedException {
		for (WebElement product: products) {
			if (product.getText().equalsIgnoreCase(ProductName)) {
				 JavascriptExecutor js = (JavascriptExecutor) driver;
		         js.executeScript("arguments[0].scrollIntoView({block: 'center'});", product);
		         
		         Thread.sleep(500);
		         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		         wait.until(ExpectedConditions.elementToBeClickable(product)).click();
				break;
			}
		}
	}
	
	// returns no results message text
	public String getNoResultMsg() {
		try {
		return msgNoResults.getText();
		} catch (Exception e) {
			return "";
		}
		
		}
	
}
