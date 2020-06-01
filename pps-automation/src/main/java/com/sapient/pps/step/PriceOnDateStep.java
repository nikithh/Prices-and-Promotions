/**
 * @author : Srinidhi

 * @see : This is for Price On Date Step 
 * @since : April 2020
 **/
package com.sapient.pps.step;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import static org.junit.Assert.assertEquals;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sapient.pps.pom.PriceOnDatePOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PriceOnDateStep {

	private WebDriver driver;
	private PriceOnDatePOM podPom;
	private String URL;
	private ExtentTest log = null;
	private ExtentReports report = null;

	public PriceOnDateStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName());
		podPom = new PriceOnDatePOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/priceondate.html");
		log = report.startTest("ExtentDemo");
	}

	@Given("^navigate to retailer login page for assigning price on date$")
	public void navigate_to_retailer_login_page_for_assigning_price_on_date() throws Throwable {
		driver.get(URL);
	}

	@When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for assigning price on date$")
	public void user_logged_in_using_as_user_and_password_as_for_assigning_price_on_date(String username,
			String password) throws Throwable {
		podPom.sendUserName(username);
		podPom.sendPassword(password);
		podPom.clickLoginBtn();
	}

	@Then("^home page should be displayed for assigning price on date$")
	public void home_page_should_be_displayed_for_assigning_price_on_date() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", podPom.header());
	}
	
	@When("^prices dropdown is selected$")
	public void prices_dropdown_is_selected() throws Throwable {
	    podPom.priceDropdownBtn();
	}

	@When("^price on date is selected$")
	public void price_on_date_is_selected() throws Throwable {
		podPom.priceOnDateBtn();
	}

	@Then("^price on date modal is displayed$")
	public void price_on_date_modal_is_displayed() throws Throwable {
		assertEquals("Select a Product", podPom.priceOnDateModal());
	}

	@When("^product name is entered as \"([^\"]*)\" for price on date$")
	public void product_name_is_entered_as_for_price_on_date(String product) throws Throwable {
		podPom.product(product);
		Thread.sleep(1000);
	}

	@When("^assign price to product is clicked$")
	public void assign_price_to_product_is_clicked() throws Throwable {
		podPom.clickAssignBtn();
	}

	@Then("^it should route to next page with product details and promotion details$")
	public void it_should_route_to_next_page_with_product_details_and_promotion_details() throws Throwable {
		assertEquals("Assign Price To Product", podPom.promotionModal());
	}

	@When("^start date is entered as \"([^\"]*)\" and End date is entered as \"([^\"]*)\" and percentage as \"([^\"]*)\"$")
	public void start_date_is_entered_as_and_End_date_is_entered_as_and_percentage_as(String startDate, String endDate,
			String percentage) throws Throwable {
		podPom.data(startDate, endDate, percentage);
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@When("^save is clicked$")
	public void save_is_clicked() throws Throwable {
		podPom.saveBtn();
	}

	@Then("^price assignment is complete$")
	public void price_assignment_is_complete() throws Throwable {
		report.endTest(log);
		report.flush();
        driver.quit();
	}
}
