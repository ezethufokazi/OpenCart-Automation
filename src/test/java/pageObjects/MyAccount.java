package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MyAccount extends BasePage {

    public MyAccount(WebDriver driver) {
        super(driver);
    }

    // ===== Locators =====
    @FindBy(xpath="//h1[normalize-space()='My Account']")
    WebElement headingMyAcc;

    @FindBy(xpath="//a[@class='list-group-item'][normalize-space()='Logout']")
    WebElement lnkLogout;

    // ===== Action Methods =====

    // returns true if My Account heading is displayed
    public boolean myAccExists() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.visibilityOf(headingMyAcc));
            return headingMyAcc.isDisplayed();
        } catch(Exception e) {
            return false;
        }
    }

    // returns My Account heading text for verification
    public String getMyAccHeading() {
        return headingMyAcc.getText();
    }

    public void clickLogout() {
        lnkLogout.click();
    }
}