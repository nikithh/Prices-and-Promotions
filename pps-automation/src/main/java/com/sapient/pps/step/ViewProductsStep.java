package com.sapient.pps.step;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sapient.pps.pom.ViewProductsPOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ViewProductsStep {

	private final WebDriver driver;
	private final ViewProductsPOM vendorPom;
	private final String URL;
	private ExtentTest log = null;
	private ExtentReports report = null;

	public ViewProductsStep() throws IOException {
		final Properties prop = new Properties();
		final FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		this.URL = prop.getProperty("URL");
		this.driver = DriverFactory.getDriver(Driver.CHROME.getName());
		this.vendorPom = new ViewProductsPOM(this.driver);
		this.report = new ExtentReports("./target/cucumber-reports/Screenshots/ViewProductsTestReport.html");
		this.log = this.report.startTest("ExtentDemo");
	}

	@Given("^navigate to vendor login for view products$")
	public void navigate_to_vendor_login_for_view_products() throws Throwable {
		this.driver.get(this.URL);
	}

	@When("^vendor login button is clicked$")
	public void vendor_login_button_is_clicked() throws Throwable {
		this.vendorPom.clickVendorBtn();
	}

	@When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for viewing products$")
	public void user_logged_in_using_as_user_and_password_as_for_viewing_products(final String username,
			final String password) throws Throwable {
		this.vendorPom.sendUserName(username);
		this.vendorPom.sendPassword(password);
		this.vendorPom.clickLoginBtn();
	}

	@Then("^home page should be displayed for vendor to view products$")
	public void home_page_should_be_displayed_for_vendor_to_view_products() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", this.vendorPom.vendorModal());
	}

	@When("^view products button is clicked$")
	public void view_products_button_is_clicked() throws Throwable {
		this.vendorPom.clickViewProduct();
//		this.log.log(LogStatus.PASS,
//				this.log.addScreenCapture(CaptureScreenShot.takeScreenshot(this.driver)) + "Test Passed");
	}

	@Then("^products modal is opened$")
	public void products_modal_is_opened() throws Throwable {
		assertEquals("View All Products", this.vendorPom.getProductModal());
		Thread.sleep(3000);
		this.log.log(LogStatus.PASS,
				this.log.addScreenCapture(CaptureScreenShot.takeScreenshot(this.driver)) + "Test Passed");
		this.report.endTest(this.log);
		this.report.flush();
		this.driver.quit();
	}
}
