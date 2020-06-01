/**
 * @author : Preeti Agrawal
 * @see : This class is for cancel not effective price change Step 
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
import com.sapient.pps.pom.CancelNotEffectivePriceChangePOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CancelNotEffectivePriceChangeStep {

	private WebDriver driver;
	private CancelNotEffectivePriceChangePOM noEPOM;
	private String URL;
	private ExtentTest log = null;
	private ExtentReports report = null;

	public CancelNotEffectivePriceChangeStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName());
		noEPOM = new CancelNotEffectivePriceChangePOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/CancelNotEffectivePriceChange.html");
		log = report.startTest("ExtentDemo");
	}

	@Given("^navigate to retailer login page for cancel not effective price change$")
	public void navigate_to_retailer_login_page_for_cancel_not_effective_price_change() throws Throwable {
		driver.get(URL);
	}

	@When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for canceling not effective price change$")
	public void user_logged_in_using_as_user_and_password_as_for_canceling_not_effective_price_change(String username,
			String password) throws Throwable {
		noEPOM.sendUserName(username);
		noEPOM.sendPassword(password);
		noEPOM.clickLoginBtn();
	}

	@Then("^home page should be displayed for canceling not effective price change$")
	public void home_page_should_be_displayed_for_canceling_not_effective_price_change() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", noEPOM.header());
	}
	
	@When("^price dropdown is selected and opens$")
	public void price_dropdown_is_selected_and_opens() throws Throwable {
	    noEPOM.priceBtn();
	}

	@Then("^cancel not effective price change button is clicked on nav bar$")
	public void cancel_not_effective_price_change_button_is_clicked_on_nav_bar() throws Throwable {
		noEPOM.clickCancelPrice();
	}

	@Then("^cancel not effective price change modal is displayed$")
	public void cancel_not_effective_price_change_modal_is_displayed() throws Throwable {
		assertEquals("Cancel Not Effective Price Change", noEPOM.cancelPricePage());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
	}

	@When("^cancel price change button is clicked$")
	public void cancel_price_change_button_is_clicked() throws Throwable {
		noEPOM.withdrawPrice();
		driver.switchTo().alert().accept();
	}

	@Then("^promotion gets deleted and same page is shown here$")
	public void promotion_gets_deleted_and_same_page_is_shown_here() throws Throwable {
		assertEquals("Cancel Not Effective Price Change", noEPOM.cancelPricePage());
		report.endTest(log);
		report.flush();
		driver.quit();
	}
}
