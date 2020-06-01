/**
 * @author : Srinidhi
 * @see : This class is for cancelling percentage promotion Step 
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
import com.sapient.pps.pom.CancelPromotionPOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CancelPromotionStep {

	private WebDriver driver;
	private CancelPromotionPOM cpPom;
	private String URL;
	private ExtentTest log = null;
	private ExtentReports report = null;

	public CancelPromotionStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName());
		cpPom = new CancelPromotionPOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/cancelpromotiontestreport.html");
		log = report.startTest("ExtentDemo");
	}

	@Given("^navigate to login page for cancelling promotion$")
	public void navigate_to_login_page_for_cancelling_promotion() throws Throwable {
		driver.get(URL);
	}

	@When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for cancelling promotion$")
	public void user_logged_in_using_as_user_and_password_as_for_cancelling_promotion(String username, String password)
			throws Throwable {
		cpPom.sendUserName(username);
		cpPom.sendPassword(password);
		cpPom.clickLoginBtn();
	}

	@Then("^home page should be displayed for cancelling promotion$")
	public void home_page_should_be_displayed_for_cancelling_promotion() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", cpPom.header());
	}
	
	@When("^promotion dropdown clicked$")
	public void promotion_dropdown_clicked() throws Throwable {
	    cpPom.promotionBtn();
	}

	@When("^cancel percentage promotion in zone level is selected$")
	public void cancel_percentage_promotion_in_zone_level_is_selected() throws Throwable {
		cpPom.clickCpBtn();
	}

	@Then("^cancel promotion modal is displayed$")
	public void cancel_promotion_modal_is_displayed() throws Throwable {
		assertEquals("Cancel Promotion Percentage", cpPom.promotionModal());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
	}

	@When("^product name is selected as \"([^\"]*)\",zone name as zoneName$")
	public void product_name_is_selected_as_zone_name_as_zoneName(String productName) throws Throwable {
		cpPom.selection(productName);
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
		Thread.sleep(2000);
	}

	@When("^go button is clicked for cancelling promotion$")
	public void go_button_is_clicked_for_cancelling_promotion() throws Throwable {
		cpPom.clickGoBtn();
	}

	@Then("^cancel promotion table modal is displayed$")
	public void cancel_promotion_table_modal_is_displayed() throws Throwable {
		assertEquals("Promotions", cpPom.promotionTableModal());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
	}

	@When("^selecting cancel promotion button$")
	public void selecting_cancel_promotion_button() throws Throwable {
		report.endTest(log);
		report.flush();
	}

	@When("^clicking on okay for promotion cancellation$")
	public void clicking_on_okay_for_promotion_cancellation() throws Throwable {
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
	}

	@Then("^the promotion is cancelled$")
	public void the_promotion_is_cancelled() throws Throwable {
		driver.quit();
	}

	@When("^clicking cancel for promotion cancellation$")
	public void clicking_cancel_for_promotion_cancellation() throws Throwable {
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
	}

	@Then("^the promotion is not cancelled$")
	public void the_promotion_is_not_cancelled() throws Throwable {
		driver.quit();
	}
}
