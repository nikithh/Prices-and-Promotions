/**
 * @author : Zubin
 * @see : This class is for Promotion In Zone 
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
import com.sapient.pps.pom.PromotionInZonePOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PromotionInZoneStep {

	private WebDriver driver;
	private PromotionInZonePOM pzPom;
	private String URL;
	private ExtentTest log = null;
	private ExtentReports report = null;

	public PromotionInZoneStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName());
		pzPom = new PromotionInZonePOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/promotioninzonetestreport.html");
		log = report.startTest("ExtentDemo");
	}

	@Given("^navigate to login page for promotion in zone level$")
	public void navigate_to_login_page_for_promotion_in_zone_level() throws Throwable {
		driver.get(URL);
	}

	@When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for zone level promotion$")
	public void user_logged_in_using_as_user_and_password_as_for_zone_level_promotion(String username, String password)
			throws Throwable {
		pzPom.sendUserName(username);
		pzPom.sendPassword(password);
		pzPom.clickLoginBtn();
	}

	@Then("^home page should be displayed for zone level promotion$")
	public void home_page_should_be_displayed_for_zone_level_promotion() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", pzPom.header());
	}
	
	@When("^promotion button is clicked$")
	public void promotion_button_is_clicked() throws Throwable {
	    pzPom.promotionBtn();
	}

	@When("^apply promotion in zone level is selected$")
	public void apply_promotion_in_zone_level_is_selected() throws Throwable {
		pzPom.clickZoneBtn();
	}

	@Then("^promotion product modal is displayed$")
	public void promotion_product_modal_is_displayed() throws Throwable {
		assertEquals("Select product name and zone", pzPom.productModal());
	}

	@When("^zone name is selected and product name is inserted as \"([^\"]*)\"$")
	public void zone_name_is_selected_and_product_name_is_inserted_as(String productName) throws Throwable {
		pzPom.selection(productName);
	}

	@Then("^check if requirements are met$")
	public void check_if_requirements_are_met() throws Throwable {
		assertEquals("Requirements", pzPom.reqCheck());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
	}

	@When("^go button is clicked for applying promotion in zone level$")
	public void go_button_is_clicked_for_applying_promotion_in_zone_level() throws Throwable {
		pzPom.clickGoBtn();
		Thread.sleep(2000);
	}

	@Then("^Apply percentage promotion modal is displayed$")
	public void apply_percentage_promotion_modal_is_displayed() throws Throwable {
		assertEquals("Apply Percentage Promotion", pzPom.promotionModal());
	}

	@When("^selecting promotion percentage as \"([^\"]*)\",start date as \"([^\"]*)\" and end date as \"([^\"]*)\"$")
	public void selecting_promotion_percentage_as_start_date_as_and_end_date_as(String promotionPercentage,
			String startDate, String endDate) throws Throwable {
		pzPom.selection1(promotionPercentage, startDate, endDate);
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
	}

	@When("^Apply promotion to zone button is clicked$")
	public void apply_promotion_to_zone_button_is_clicked() throws Throwable {
		pzPom.applyPromotionBtn();
	}

	@Then("^promotions in zone level table is displayed$")
	public void promotions_in_zone_level_table_is_displayed() throws Throwable {
		assertEquals("Promotions in zone level", pzPom.tableModal());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
		report.endTest(log);
		report.flush();
		driver.quit();
	}
}
