package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccount;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDataDriven extends BaseClass {

    @Test(
        dataProvider = "dp",
        dataProviderClass = DataProviders.class,
        groups = {"Datadriven", "Master"}
    )
    public void verify_loginDDT(String email, String password,
            String expectedResult, String scenario) {
        try {
            logger.info("**** Starting TC003_LoginDataDriven ****");
            logger.info("Scenario: " + scenario);
            logger.info("Email: " + email
                + " | Password: " + (password.isEmpty() ? "[empty]" : "[provided]")
                + " | Expected: " + expectedResult);

            // Navigate to login page
            HomePage hp = new HomePage(driver);
            hp.clickLogin();
            logger.info("Navigated to login page");

            // Enter credentials
            LoginPage lp = new LoginPage(driver);
            lp.setEmail(email);
            lp.setPassword(password);
            lp.clickLoginbtn();
            logger.info("Login attempted");

            // Check if login was successful
            MyAccount mc = new MyAccount(driver);
            boolean loginSuccessful = mc.myAccExists();

            if(expectedResult.equalsIgnoreCase("Valid")) {
                if(loginSuccessful) {
                    logger.info("PASS - " + scenario
                        + ": Login successful with valid credentials");
                    Assert.assertTrue(true);
                    mc.clickLogout();
                    logger.info("Logged out successfully");
                } else {
                    logger.error("FAIL - " + scenario
                        + ": Login failed with valid credentials");
                    Assert.fail("Login failed with valid credentials | "
                        + "Scenario: " + scenario + " | Email: " + email);
                }
            } else if(expectedResult.equalsIgnoreCase("Invalid")) {
                if(loginSuccessful) {
                    logger.error("FAIL - " + scenario
                        + ": Login succeeded with invalid credentials");
                    mc.clickLogout();
                    Assert.fail("Login succeeded with invalid credentials | "
                        + "Scenario: " + scenario + " | Email: " + email);
                } else {
                    logger.info("PASS - " + scenario
                        + ": Login correctly rejected");
                    Assert.assertTrue(true);
                }
            } else {
                logger.warn("Unknown expected result: " + expectedResult);
                Assert.fail("Invalid expected result in Excel: "
                    + expectedResult + " | Scenario: " + scenario);
            }

            logger.info("**** TC003_LoginDataDriven PASSED - "
                + scenario + " ****");

        } catch(Exception e) {
            logger.error("TC003_LoginDataDriven FAILED | Scenario: "
                + scenario + " | Error: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }
}