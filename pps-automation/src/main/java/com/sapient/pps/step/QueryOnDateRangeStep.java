/**
 * @author : Zubin
 * @see : This class is for Query on date range Step 
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
import com.sapient.pps.pom.QueryOnDateRangePOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class QueryOnDateRangeStep {
	private WebDriver driver;
	private QueryOnDateRangePOM qPom;
	private String URL;
	private ExtentTest log = null;
	private ExtentReports report = null;

	public QueryOnDateRangeStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		report = new ExtentReports("./target/cucumber-reports/Screenshots/queryondaterangetestreport.html");
		log = report.startTest("ExtentDemo");
	}

	@Given("^navigate to retailer login page for querying date range$")
	public void navigate_to_retailer_login_page_for_querying_date_range() throws Throwable {
		driver = DriverFactory.getDriver(Driver.CHROME.getName());
		qPom = new QueryOnDateRangePOM(driver);
		driver.get(URL);
	}

	@When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for querying date range$")
	public void user_logged_in_using_as_user_and_password_as_for_querying_date_range(String username, String password)
			throws Throwable {
		qPom.sendUserName(username);
		qPom.sendPassword(password);
		qPom.clickLoginBtn();
	}

	@Then("^home page should be displayed for querying date range$")
	public void home_page_should_be_displayed_for_querying_date_range() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", qPom.header());
	}
	
	@When("^promotion button is selected$")
	public void promotion_button_is_selected() throws Throwable {
	    qPom.promotionBtn();
	}

	@Then("^query for date range button is clicked on nav bar$")
	public void query_for_date_range_button_is_clicked_on_nav_bar() throws Throwable {
		qPom.queryOnDateRangeBtn();
	}

	@Then("^Promotions for Products page is displayed$")
	public void promotions_for_Products_page_is_displayed() throws Throwable {
		assertEquals("Promotions on Date Range", qPom.queryOnDateRangeModal());
	}

	@When("^Start date is entered as \"([^\"]*)\" and End date is entered as \"([^\"]*)\"$")
	public void start_date_is_entered_as_and_End_date_is_entered_as(String startDate, String endDate) throws Throwable {
		qPom.startDate(startDate);
		qPom.endDate(endDate);
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
	}

	@When("^Zone is selected$")
	public void Zone_is_selected() throws Throwable {
		qPom.selectZone();
	}

	@When("^Cluster is selected$")
	public void Cluster_is_selected() throws Throwable {
		qPom.selectCluster();
	}

	@Then("^Show promotions button is clicked$")
	public void show_promotions_button_is_clicked() throws Throwable {
		qPom.showButton();
	}

	@Then("^Promotions for Products page is shown$")
	public void promotions_for_Products_page_is_shown() throws Throwable {
		assertEquals("Promotions on Date Range", qPom.promotionsTab());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
		driver.quit();
	}

	@Then("^End Date has to be greater than Start Date criteria is not met$")
	public void end_Date_has_to_be_greater_than_Start_Date_criteria_is_not_met() throws Throwable {
		qPom.endDateReq();
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
		report.endTest(log);
		report.flush();
		driver.quit();

	}
}