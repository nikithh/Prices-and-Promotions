/**
 * @author : Srinidhi
 * @see : This class is for create zone Step 
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
import com.sapient.pps.pom.CreateZonePOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CreateZoneStep {
	private WebDriver driver;
	private CreateZonePOM zonePom;
	private String URL;
	private ExtentTest log = null;
	private ExtentReports report = null;

	public CreateZoneStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName());
		zonePom = new CreateZonePOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/createstoretestreport.html");
		log = report.startTest("ExtentDemo");
	}

	@Given("^navigate to retailer login page for zone$")
	public void navigate_to_vendor_login_page_for_zone() throws Throwable {
		driver.get(URL);
	}

	@When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for zone$")
	public void user_logged_in_using_as_user_and_password_as_for_zone(String username, String password)
			throws Throwable {
		zonePom.sendUserName(username);
		zonePom.sendPassword(password);
		zonePom.clickLoginBtn();
	}

	@Then("^home page should be displayed for zone$")
	public void home_page_should_be_displayed_for_zone() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", zonePom.header());
	}
	
	@When("^location button is clicked$")
	public void location_button_is_clicked() throws Throwable {
	    zonePom.locationBtn();
	}

	@When("^create zone is selected$")
	public void create_zone_is_selected() throws Throwable {
		zonePom.createzone();
	}

	@Then("^create zone modal is displayed$")
	public void create_zone_modal_is_displayed() throws Throwable {
		assertEquals("Create a Zone", zonePom.zonesModal());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
	}

	@When("^zone value is entered as \"([^\"]*)\" and price per unit is entered as \"([^\"]*)\"$")
	public void zone_value_is_entered_as_and_price_per_unit_is_entered_as(String zoneName, String lppu)
			throws Throwable {
		zonePom.sendZoneName(zoneName);
		zonePom.sendPpu(lppu);
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
	}

	@When("^save button is clicked$")
	public void save_button_is_clicked() throws Throwable {
		zonePom.clickSaveBtn();
	}

	@Then("^zone creation failed should be displayed$")
	public void zone_creation_failed_should_be_displayed() throws Throwable {
		//assertEquals("Zone creation failed. Please match the requirements", zonePom.failMsg());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
		driver.quit();
	}

	@Then("^zone created succesfully should be displayed$")
	public void zone_created_succesfully_should_be_displayed() throws Throwable {
		//assertEquals("Zone Created Successfully", zonePom.successMsg());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
		report.endTest(log);
		report.flush();
		driver.quit();
	}
}