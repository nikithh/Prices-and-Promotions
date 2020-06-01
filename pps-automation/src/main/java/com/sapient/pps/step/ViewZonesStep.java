/**
 * @author : Zubin

 * @see : This class is for view zones Step 
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
import com.sapient.pps.pom.ViewZonesPOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ViewZonesStep {
	private WebDriver driver;
	private ViewZonesPOM zPom;
	private String URL;
	private ExtentTest log = null;
	private ExtentReports report = null;

	public ViewZonesStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName());
		zPom = new ViewZonesPOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/viewzonestestreport.html");
		log = report.startTest("ExtentDemo");
	}

	@Given("^navigate to retailer login page for viewing zone$")
	public void navigate_to_retailer_login_page_for_viewing_zone() throws Throwable {
		driver.get(URL);
	}

	@When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for veing zone$")
	public void user_logged_in_using_as_user_and_password_as_for_veing_zone(String username, String password)
			throws Throwable {
		zPom.sendUserName(username);
		zPom.sendPassword(password);
		zPom.clickLoginBtn();
	}

	@Then("^home page should be displayed for viewing zone$")
	public void home_page_should_be_displayed_for_viewing_zone() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", zPom.header());
	}
	
	@When("^location dropdown is clicked$")
	public void location_dropdown_is_clicked() throws Throwable {
	    zPom.locationBtn();
	}

	@When("^view zones is selected$")
	public void view_zones_is_selected() throws Throwable {
		zPom.viewZonesBtn();
	}

	@Then("^view zones modal opens$")
	public void view_zones_modal_opens() throws Throwable {
		assertEquals("Showing All Zones", zPom.zonesModal());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
		report.endTest(log);
		report.flush();
		driver.quit();
	}
}
