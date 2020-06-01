/**
 * @author : Srinidhi

 * @see : This class is for sell cancel product Step 
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
import com.sapient.pps.pom.SellCancelProductPOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SellCancelProductStep {

	private WebDriver driver;
	private SellCancelProductPOM sellCancelPom;
	private String URL;
	private ExtentTest log = null;
	private ExtentReports report = null;

	public SellCancelProductStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName());
		sellCancelPom = new SellCancelProductPOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/SellCancelProductTestReport.html");
		log = report.startTest("ExtentDemo");
	}

	@Given("^navigate to retailer login form$")
	public void navigate_to_retailer_login_form() throws Throwable {
		driver.get(URL);
	}

	@When("^retailer logged in using \"([^\"]*)\" as username and \"([^\"]*)\" as password$")
	public void retailer_logged_in_using_as_username_and_as_password(String username, String password)
			throws Throwable {
		sellCancelPom.sendUserName(username);
		sellCancelPom.sendPassword(password);
		sellCancelPom.clickLoginBtn();
	}

	@Then("^retailer modal is opened$")
	public void retailer_modal_is_opened() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", sellCancelPom.header());
	}
	
	@When("^price button is clicked$")
	public void price_button_is_clicked() throws Throwable {
	    sellCancelPom.clickPriceBtn();
	}

	@When("^clicked on sell/cancel product button$")
	public void clicked_on_sell_cancel_product_button() throws Throwable {
		sellCancelPom.clickSellCancelBtn();
	}

	@Then("^sell/cancel product modal is opened$")
	public void sell_cancel_product_modal_is_opened() throws Throwable {
		assertEquals("Select a Product", sellCancelPom.productModal());
	}

	@When("^a baby product is selected as \"([^\"]*)\" to sell/cancel$")
	public void a_baby_product_is_selected_as_to_sell_cancel(String product) throws Throwable {
		sellCancelPom.product(product);
		Thread.sleep(3000);
	}

	@When("^go button is clicked$")
	public void go_button_is_clicked() throws Throwable {
		sellCancelPom.clickGoBtn();
	}

	@Then("^it routed to the next page with product description$")
	public void it_routed_to_the_next_page_with_product_description() throws Throwable {
		sellCancelPom.prodDescModal();
	}

	@When("^sell product button is clicked$")
	public void sell_product_button_is_clicked() throws Throwable {
		sellCancelPom.clickSellBtn();
	}

	@When("^selected okay in the alert shown to sell$")
	public void selected_okay_in_the_alert_shown_to_sell() throws Throwable {
		driver.switchTo().alert().accept();
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@Then("^product is sold$")
	public void product_is_sold() throws Throwable {
		//assertEquals("Product is sold", sellCancelPom.soldSuccessMsg());
		driver.quit();
	}

	@When("^cancel product button is clicked$")
	public void cancel_product_button_is_clicked() throws Throwable {
		sellCancelPom.clickCancelBtn();
	}

	@When("^selected okay in the alert shown to cancel$")
	public void selected_okay_in_the_alert_shown_to_cancel() throws Throwable {
		driver.switchTo().alert().accept();
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@Then("^product is cancelled$")
	public void product_is_cancelled() throws Throwable {
		//assertEquals("product is cancelled", sellCancelPom.cancelSucessMsg());
		driver.quit();
	}
}