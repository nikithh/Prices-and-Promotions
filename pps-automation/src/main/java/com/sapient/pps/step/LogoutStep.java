/**
 * @author : Zubin

 * @see : This is for Logout Step 
 * @since : April 2020
 **/
package com.sapient.pps.step;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sapient.pps.pom.LogoutPOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LogoutStep {
	private WebDriver driver;
	private LogoutPOM lPom;
	private String URL;
	private ExtentTest log = null;
	private ExtentReports report = null;

	public LogoutStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName());
		lPom = new LogoutPOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/logouttestreport.html");
		log = report.startTest("ExtentDemo");
	}

	@Given("^navigate to retailer login$")
	public void navigate_to_retailer_login() throws Throwable {
		driver.get(URL);
	}

	@When("^user logged in using \"([^\"]*)\" and password as \"([^\"]*)\"$")
	public void user_logged_in_using_and_password_as(String username, String password) throws Throwable {
		lPom.sendUserName(username);
		lPom.sendPassword(password);
		lPom.clickLoginBtn();
	}

	@Then("^home page is shown$")
	public void home_page_is_shown() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", lPom.header());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
	}

	@Then("^LOGOUT button is clicked$")
	public void logout_button_is_clicked() throws Throwable {
		lPom.clickLogoutBtn();
	}

	@Then("^home page should be shown again$")
	public void home_page_should_be_shown_again() throws Throwable {
		assertEquals("LOGIN", lPom.loginBtnText());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
		report.endTest(log);
		report.flush();
		driver.quit();
	}

}
