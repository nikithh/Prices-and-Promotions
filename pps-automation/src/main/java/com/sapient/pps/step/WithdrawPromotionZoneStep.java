/**
 * @author : Preeti

 * @see : This class is for withdrawing promotion from zone 
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
import com.sapient.pps.pom.WithdrawPromotionZonePOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class WithdrawPromotionZoneStep {

	private WebDriver driver;
	private WithdrawPromotionZonePOM wpPom;
	private String URL;
	private ExtentTest log = null;
	private ExtentReports report = null;

	public WithdrawPromotionZoneStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName());
		wpPom = new WithdrawPromotionZonePOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/withdrawpercentagepromotionzonetestreport.html");
		log = report.startTest("ExtentDemo");
	}

	@Given("^navigate to login page for withdrawing promotion in zone level$")
	public void navigate_to_login_page_for_withdrawing_promotion_in_zone_level() throws Throwable {
		driver.get(URL);
	}

	@Then("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for withdrawing zone level promotion$")
	public void user_logged_in_using_as_user_and_password_as_for_withdrawing_zone_level_promotion(String username,
			String password) throws Throwable {
		wpPom.sendUserName(username);
		wpPom.sendPassword(password);
		wpPom.clickLoginBtn();
	}

	@Then("^home page should be displayed for withdrawing zone level promotion$")
	public void home_page_should_be_displayed_for_withdrawing_zone_level_promotion() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", wpPom.header());
	}
	
	@When("^promotion dropdown is selected$")
	public void promotion_dropdown_is_selected() throws Throwable {
	    wpPom.promotionBtn();
	}

	@When("^withdraw promotion in zone level is selected$")
	public void withdraw_promotion_in_zone_level_is_selected() throws Throwable {
		wpPom.clickClusterPromotion();
	}

	@Then("^withdraw promotion from zone modal is displayed$")
	public void withdraw_promotion_from_zone_modal_is_displayed() throws Throwable {
		wpPom.productModal();
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
	}

	@When("^product name is selected as \"([^\"]*)\"$")
	public void product_name_is_selected_as(String productName) throws Throwable {
		wpPom.selection(productName);
		Thread.sleep(1000);
	}

	@When("^go button is clicked for withdrawing promotion from zone level$")
	public void go_button_is_clicked_for_withdrawing_promotion_from_zone_level() throws Throwable {
		wpPom.clickGoBtn();
	}

	@Then("^promotion in zone level table is displayed$")
	public void promotion_in_zone_level_table_is_displayed() throws Throwable {
		assertEquals("Promotions in Zone Level", wpPom.promotionModal());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
	}

	@When("^withdraw button is clicked$")
	public void withdraw_button_is_clicked() throws Throwable {
		//wpPom.withdrawButton();
	}

	@When("^clicking on ok button$")
	public void clicking_on_ok_button() throws Throwable {
		driver.quit();
	}


	@When("^clicking on cancel button$")
	public void clicking_on_cancel_button() throws Throwable {
		driver.quit();
	}

}
