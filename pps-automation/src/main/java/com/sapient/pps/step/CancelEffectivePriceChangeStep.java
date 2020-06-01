/**
 * @author : Srinidhi
 * @see : This class is for cancel effective price change Step 
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
import com.sapient.pps.pom.CancelEffectivePriceChangePOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CancelEffectivePriceChangeStep {

	private WebDriver driver;
	private CancelEffectivePriceChangePOM cepcPom;
	private String URL;
	private ExtentTest log = null;
	private ExtentReports report = null;

	public CancelEffectivePriceChangeStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		report = new ExtentReports("./target/cucumber-reports/Screenshots/CancelEffectivePriceChange.html");
		log = report.startTest("ExtentDemo");
	}

	@Given("^navigate to retailer login page for cancel effective price change$")
	public void navigate_to_retailer_login_page_for_cancel_effective_price_change() throws Throwable {
		driver = DriverFactory.getDriver(Driver.CHROME.getName());
		cepcPom = new CancelEffectivePriceChangePOM(driver);
		driver.get(URL);
	}

	@When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for cancelling effective price change$")
	public void user_logged_in_using_as_user_and_password_as_for_cancelling_effective_price_change(String username,
			String password) throws Throwable {
		cepcPom.sendUserName(username);
		cepcPom.sendPassword(password);
		cepcPom.clickLoginBtn();
	}

	@Then("^home page should be displayed for cancelling effective price change$")
	public void home_page_should_be_displayed_for_cancelling_effective_price_change() throws Throwable {
		cepcPom.header();
	}
	
	@When("^price dropdown btn is clicked$")
	public void price_dropdown_btn_is_clicked() throws Throwable {
	    cepcPom.priceBtn();
	}

	@Then("^cancel effective price change button is clicked on nav bar$")
	public void cancel_effective_price_change_button_is_clicked_on_nav_bar() throws Throwable {
		cepcPom.cancelEffectivePriceChangeBtn();
	}

	@Then("^cancel effective price change modal is displayed$")
	public void cancel_effective_price_change_modal_is_displayed() throws Throwable {
		assertEquals("Cancel Effective Price Change", cepcPom.cancelEffectivePriceChangeModal());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
	}

	@When("^withdraw button is clicked to cancel$")
	public void withdraw_button_is_clicked_to_cancel() throws Throwable {
		cepcPom.cepcButton();
		driver.switchTo().alert().accept();
	}

	@Then("^promotion gets deleted and same page is shown$")
	public void promotion_gets_deleted_and_same_page_is_shown() throws Throwable {
		assertEquals("Cancel Effective Price Change", cepcPom.cancelEffectivePriceChangeModal());
		report.endTest(log);
		report.flush();
		driver.quit();
	}
}
