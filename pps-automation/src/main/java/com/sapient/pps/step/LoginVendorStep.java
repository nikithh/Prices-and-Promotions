package com.sapient.pps.step;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sapient.pps.pom.LoginVendorPOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginVendorStep {
	
	private WebDriver driver; 
    private LoginVendorPOM lPom; 
    private String URL;
    private ExtentTest log = null;
    private ExtentReports report = null ;
    
    public LoginVendorStep() throws IOException {
	   	 Properties prop = new Properties(); 
	   	 FileInputStream inStream = new FileInputStream("./resources/config.properties");
	   	 prop.load(inStream);
	     URL = prop.getProperty("URL");
	     driver = DriverFactory.getDriver(Driver.CHROME.getName()); 
	     lPom = new LoginVendorPOM(driver);
	     report = new ExtentReports("./target/cucumber-reports/Screenshots/vendorlogintestreport.html");
		 log = report.startTest("ExtentDemo");
    }
    
	@Given("^navigate to vendor login form$")
	public void navigate_to_vendor_login_form() throws Throwable {
		 driver.get(URL);
		 lPom.clickVendorBtn();
		 log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@When("^user logged in using \"([^\"]*)\" as user and \"([^\"]*)\" as password$")
	public void user_logged_in_using_as_user_and_as_password(String username, String password) throws Throwable {
		lPom.sendUserName(username);
		lPom.sendPassword(password);
		lPom.clickLoginBtn();
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@Then("^Vendor app should be displayed$")
	public void vendor_app_should_be_displayed() throws Throwable {
		assertEquals("Add a Product", lPom.addProduct());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
		driver.quit();
	}

	@Then("^invalid popup message  showing in invalid credentials  should be displayed$")
	public void invalid_popup_message_showing_in_invalid_credentials_should_be_displayed() throws Throwable {
		assertEquals("Invalid Username/Password", lPom.wrongIp());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
		report.endTest(log);
		report.flush();
		driver.quit();
	}
}
