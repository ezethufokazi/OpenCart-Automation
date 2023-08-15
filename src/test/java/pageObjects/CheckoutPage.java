package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage extends BasePage {

    public CheckoutPage(WebDriver driver) {
        super(driver);
        // wait for checkout page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("checkout"));
    }

    // ===== GUEST/REGISTER LOCATORS =====
    @FindBy(xpath="//input[@id='input-guest']")
    WebElement btnGuestCheckout;

    @FindBy(xpath="//input[@id='input-register']")
    WebElement btnRegisterCheckout;

    @FindBy(xpath="//input[@id='input-firstname']")
    WebElement txtFirstName;

    @FindBy(xpath="//input[@id='input-lastname']")
    WebElement txtLastName;

    @FindBy(xpath="//input[@id='input-email']")
    WebElement txtEmail;

    @FindBy(xpath="//input[@id='input-shipping-address-1']")
    WebElement txtAddress1;

    @FindBy(xpath="//input[@id='input-shipping-city']")
    WebElement txtCity;

    @FindBy(xpath="//input[@id='input-shipping-postcode']")
    WebElement txtPostCode;

    @FindBy(xpath="//select[@id='input-shipping-country']")
    WebElement drpCountry;

    @FindBy(xpath="//select[@id='input-shipping-zone']")
    WebElement drpRegion;

    @FindBy(xpath="//input[@id='input-password']")
    WebElement txtPassword;

    @FindBy(xpath="//input[@id='input-register-agree']")
    WebElement chkPrivacyPolicy;

    @FindBy(xpath="//button[@id='button-register']")
    WebElement btnRegContinue;

    // ===== LOGGED IN USER LOCATORS =====
    @FindBy(xpath="//input[@id='input-shipping-existing']")
    WebElement btnExistingAddress;

    @FindBy(xpath="//input[@id='input-shipping-new']")
    WebElement btnNewAddress;

    @FindBy(xpath="//select[@id='input-shipping-address']")
    WebElement drpAddress;

    @FindBy(xpath="//input[@id='input-shipping-firstname']")
    WebElement txtShippingFirstName;

    @FindBy(xpath="//input[@id='input-shipping-lastname']")
    WebElement txtShippingLastName;

    @FindBy(xpath="//button[@id='button-shipping-address']")
    WebElement btnContinue;

    // ===== SHIPPING/PAYMENT LOCATORS =====
    @FindBy(xpath="//div[@class='alert alert-success alert-dismissible']")
    WebElement alertSuccess;

    @FindBy(xpath="//button[@id='button-shipping-methods']")
    WebElement btnShippingMethod;

    @FindBy(xpath="//button[@id='button-shipping-method']")
    WebElement btnShippingContinue;

    @FindBy(xpath="//button[@id='button-payment-methods']")
    WebElement btnPaymentMethod;

    @FindBy(xpath="//button[@id='button-payment-method']")
    WebElement btnPaymentContinue;

    @FindBy(xpath="//button[@id='button-confirm']")
    WebElement btnConfirmOrder;

    @FindBy(xpath="//h1[normalize-space()='Your order has been placed!']")
    WebElement msgOrderPlaced;

    // ===== ORDER SUMMARY LOCATORS =====
    @FindBy(xpath="//legend[normalize-space()='Shipping Address']")
    WebElement headingShippingAddress;

    @FindBy(xpath="//legend[normalize-space()='Shipping Method']")
    WebElement headingShippingMethod;

    @FindBy(xpath="//div[@class='table-responsive']//tfoot//tr[1]//td[2]")
    WebElement dtSubTotal;

    @FindBy(xpath="//div[@class='table-responsive']//tfoot//tr[2]//td[2]")
    WebElement dtFlatShippingRate;

    @FindBy(xpath="//div[@class='table-responsive']//tfoot//tr[3]//td[2]")
    WebElement dtTotal;

    // ===== GUEST/REGISTER ACTION METHODS =====

    public void selectGuestCheckout() {
        if(!btnGuestCheckout.isSelected()) btnGuestCheckout.click();
    }

    public void selectRegisterCheckout() {
        if(!btnRegisterCheckout.isSelected()) btnRegisterCheckout.click();
    }

    public void setPersonalDetails(String firstname,
            String lastname, String email) {
        txtFirstName.clear(); txtFirstName.sendKeys(firstname);
        txtLastName.clear();  txtLastName.sendKeys(lastname);
        txtEmail.clear();     txtEmail.sendKeys(email);
    }

    public void setShippingAddress(String address1, String city,
            String postcode, String country, String region) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);",
            headingShippingAddress);
        txtAddress1.clear();  txtAddress1.sendKeys(address1);
        txtCity.clear();      txtCity.sendKeys(city);
        txtPostCode.clear();  txtPostCode.sendKeys(postcode);

        // select country and wait for region to load
        Select countryDropdown = new Select(drpCountry);
        countryDropdown.selectByVisibleText(country);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(drpRegion));
        Select regionDropdown = new Select(drpRegion);
        regionDropdown.selectByVisibleText(region);
    }

    public void setPassword(String password) {
        txtPassword.clear();
        txtPassword.sendKeys(password);
    }

    public void setPrivacyPolicy() {
        if(!chkPrivacyPolicy.isSelected()) chkPrivacyPolicy.click();
    }

    public void clickRegContinue() {
        btnRegContinue.click();
    }

    public boolean getInformationSavedMsg() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(alertSuccess));
            return alertSuccess.isDisplayed();
        } catch(Exception e) {
            return false;
        }
    }

    // ===== LOGGED IN USER ACTION METHODS =====

    public void selectExistingAddress() {
        if(!btnExistingAddress.isSelected()) btnExistingAddress.click();
    }

    public void selectNewAddress() {
        if(!btnNewAddress.isSelected()) btnNewAddress.click();
    }

    public void selectAddress() {
        Select addressDropdown = new Select(drpAddress);
        addressDropdown.selectByIndex(1);
    }

    public boolean isExistingAddressDisplayed() {
        try {
            return btnExistingAddress.isDisplayed();
        } catch(Exception e) {
            return false;
        }
    }

    public void setLoggedInShippingAddress(String firstname,
            String lastname, String address1, String city,
            String postcode, String country, String region) {
        txtShippingFirstName.clear();
        txtShippingFirstName.sendKeys(firstname);
        txtShippingLastName.clear();
        txtShippingLastName.sendKeys(lastname);
        setShippingAddress(address1, city, postcode, country, region);
    }

    public void clickContinue() {
        btnContinue.click();
    }

    // ===== SHIPPING/PAYMENT ACTION METHODS =====

    public void selectShippingMethod() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);",
            headingShippingMethod);
        wait.until(ExpectedConditions.elementToBeClickable(btnShippingMethod));
        btnShippingMethod.click();
        wait.until(ExpectedConditions.visibilityOf(btnShippingContinue));
        btnShippingContinue.click();
    }

    public void selectPaymentMethod() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOf(btnShippingContinue));
        btnPaymentMethod.click();
        wait.until(ExpectedConditions.visibilityOf(btnPaymentContinue));
        btnPaymentContinue.click();
    }

    public boolean verifyTotalPrice() {
        try {
            double subTotal = parsePrice(dtSubTotal.getText());
            double shipping = parsePrice(dtFlatShippingRate.getText());
            double total    = parsePrice(dtTotal.getText());
            return total == (subTotal + shipping);
        } catch(Exception e) {
            return false;
        }
    }

    private double parsePrice(String price) {
        return Double.parseDouble(
            price.replace("$", "").replace(",", "").trim());
    }

    public void confirmOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);",
            headingShippingMethod);
        wait.until(ExpectedConditions.elementToBeClickable(btnConfirmOrder));
        btnConfirmOrder.click();
    }

    public boolean isOrderPlaced() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.visibilityOf(msgOrderPlaced));
            return msgOrderPlaced.isDisplayed();
        } catch(Exception e) {
            return false;
        }
    }
}