/**
 * @author : Zubin
 * @see : This class is for view clusters Step 
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
import com.sapient.pps.pom.ViewClustersPOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ViewClustersStep {

	private WebDriver driver;
	private ViewClustersPOM cPom;
	private String URL;
	private ExtentTest log = null;
	private ExtentReports report = null;

	public ViewClustersStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName());
		cPom = new ViewClustersPOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/viewclusterstestreport.html");
		log = report.startTest("ExtentDemo");
	}

	@Given("^navigate to retailer login page for viewing cluster$")
	public void navigate_to_retailer_login_page_for_viewing_cluster() throws Throwable {
		driver.get(URL);
	}

	@When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for viewing cluster$")
	public void user_logged_in_using_as_user_and_password_as_for_viewing_zone(String username, String password)
			throws Throwable {
		cPom.sendUserName(username);
		cPom.sendPassword(password);
		cPom.clickLoginBtn();
	}

	@Then("^home page should be displayed for viewing cluster$")
	public void home_page_should_be_displayed_for_viewing_cluster() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", cPom.header());
	}
	
	@When("^location is selected$")
	public void location_is_selected() throws Throwable {
	    cPom.locationBtn();
	}

	@When("^view clusters is selected$")
	public void view_clusters_is_selected() throws Throwable {
		cPom.viewClustersBtn();
	}

	@Then("^view clusters modal opens$")
	public void view_clusters_modal_opens() throws Throwable {
		assertEquals("Showing All Clusters", cPom.clustersModal());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
	}

	@Then("^user logs out$")
	public void user_logs_out() throws Throwable {
		cPom.clickLogoutBtn();
	}

	@Then("^retailer log in is shown again$")
	public void retailer_log_in_is_shown_again() throws Throwable {
		assertEquals("LOGIN", cPom.loginTxt());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
		report.endTest(log);
		report.flush();
		driver.quit();
	}

}
