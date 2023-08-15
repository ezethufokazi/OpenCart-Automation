package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // ===== Locators =====
    @FindBy(xpath="//span[normalize-space()='My Account']")
    WebElement lnkMyAccount;

    @FindBy(xpath="//a[normalize-space()='Register']")
    WebElement lnkRegister;

    @FindBy(xpath="//a[@class='dropdown-item'][normalize-space()='Login']")
    WebElement lnkLogin;

    @FindBy(xpath="//input[@placeholder='Search']")
    WebElement txtSearch;

    @FindBy(xpath="//button[@type='submit']")
    WebElement btnSearch;

    // ===== Action Methods =====

    // clicks My Account dropdown
    public void clickMyAcc() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(lnkMyAccount));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", lnkMyAccount);
        Thread.sleep(2000); // wait for dropdown to fully appear
    }

    // navigates to registration page directly
    public void clickRegister() {
        driver.get("http://host.docker.internal/opencart/"
            + "index.php?route=account/register");
    }

    // navigates to login page directly
    public void clickLogin() {
        driver.get("http://host.docker.internal/opencart/"
            + "index.php?route=account/login");
    }

    // enters product name in search box
    public void enterProduct(String productName) {
        txtSearch.clear();
        txtSearch.sendKeys(productName);
    }

    // clicks search button
    public void clickSearch() {
        btnSearch.click();
    }
}