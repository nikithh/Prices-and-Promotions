/**
 * @author : Srinidhi
 * @see : This class is for dashboard Step 
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
import com.sapient.pps.pom.DashboardPOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class DashboardStep {

	private WebDriver driver;
	private DashboardPOM dashPom;
	private String URL;
	private ExtentTest log = null;
	private ExtentReports report = null;

	public DashboardStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName());
		dashPom = new DashboardPOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/Dashboardtestreport.html");
		log = report.startTest("ExtentDemo");
	}

	@Given("^navigate to retailer login page for dashboard$")
	public void navigate_to_retailer_login_page_for_dashboard() throws Throwable {
		driver.get(URL);
	}

	@When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for dashboard$")
	public void user_logged_in_using_as_user_and_password_as_for_dashboard(String username, String password) throws Throwable {
		dashPom.sendUserName(username);
		dashPom.sendPassword(password);
		dashPom.clickLoginBtn();
	}

	@Then("^home page should be displayed for dashboard$")
	public void home_page_should_be_displayed_for_dashboard() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", dashPom.header());
	}

	@When("^dashboard is selected$")
	public void dashboard_is_selected() throws Throwable {
		dashPom.clickDashboardBtn();
	}

	@Then("^dashboard modal is displayed$")
	public void dashboard_modal_is_displayed() throws Throwable {
		assertEquals("Number of Zones", dashPom.dashModalPage());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
	}

	@When("^clicked on plus button for adding zone$")
	public void clicked_on_plus_button_for_adding_zone() throws Throwable {
		dashPom.clickZoneBtn();
	}

	@Then("^it gets routed to add zone page$")
	public void it_gets_routed_to_add_zone_page() throws Throwable {
		assertEquals("Create a Zone", dashPom.zoneModalPage());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
		report.endTest(log);
		report.flush();
		driver.quit();
	}

	@When("^clicked on plus button for adding cluster$")
	public void clicked_on_plus_button_for_adding_cluster() throws Throwable {
		dashPom.clickClusterBtn();
	}

	@Then("^it gets routed to add cluster page$")
	public void it_gets_routed_to_add_cluster_page() throws Throwable {
		assertEquals("Create a cluster", dashPom.clusterModalPage());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
		report.endTest(log);
		report.flush();
		driver.quit();
	}

	@When("^clicked on plus button for adding product$")
	public void clicked_on_plus_button_for_adding_product() throws Throwable {
		dashPom.clickProductBtn();
	}

	@Then("^it gets routed to add product page$")
	public void it_gets_routed_to_add_product_page() throws Throwable {
		assertEquals("Add Products to the Store", dashPom.productModalPage());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
		report.endTest(log);
		report.flush();
		driver.quit();
	}

}
