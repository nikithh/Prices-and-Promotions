/**
 * @author : Srinidhi
 * @see : This step is for Admin login step
 * @since : April 2020
 **/
package com.sapient.pps.step;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sapient.pps.pom.AdminLoginPOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AdminLoginStep {
	
    private WebDriver driver;
    private AdminLoginPOM adminPom;
    private String URL;
    private ExtentTest log = null;
    private ExtentReports report = null ;
    
    public AdminLoginStep() throws IOException {
        Properties prop = new Properties();
        FileInputStream inStream = new FileInputStream("./resources/config.properties");
        prop.load(inStream);
        URL = prop.getProperty("URL");
        driver = DriverFactory.getDriver(Driver.CHROME.getName());
        adminPom = new AdminLoginPOM(driver);
        report = new ExtentReports("./target/cucumber-reports/Screenshots/AdminLoginReport.html");
        log = report.startTest("ExtentDemo");
    }
    
    @Given("^navigate to Admin login form$")
    public void navigate_to_Admin_login_form() throws Throwable {
        driver.get(URL);
    }

    @When("^login as admin is clicked$")
    public void login_as_admin_is_clicked() throws Throwable {
        adminPom.clickAdminLoginBtn();
    }
    
    @When("^admin logged in using \"([^\"]*)\" as username and \"([^\"]*)\" as password$")
    public void admin_logged_in_using_as_username_and_as_password(String username, String password) throws Throwable {
        adminPom.sendUserName(username);
        adminPom.sendPassword(password);
        log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
        adminPom.clickLoginBtn();
    }

    @Then("^admin app should be displayed$")
    public void admin_app_should_be_displayed() throws Throwable {
        assertEquals("PRICES AND PROMOTIONS", adminPom.header());
        log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
    }

    @When("^admin logged in using \"([^\"]*)\" as username and \"([^\"]*)\" as password as invalid$")
    public void admin_logged_in_using_as_username_and_as_password_as_invalid(String username, String password) throws Throwable {
        adminPom.sendUserName(username);
        adminPom.sendPassword(password);
        adminPom.clickLoginBtn();
    }

    @Then("^an invalid credentials popup message is shown$")
    public void an_invalid_credentials_popup_message_is_shown() throws Throwable {
        report.endTest(log);
        report.flush();
        driver.quit();
    }
}