/**
 * @author : Preeti

 * @see : This class is for update price Step 
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
import com.sapient.pps.pom.UpdatePricePOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UpdatePriceStep {

	private WebDriver driver;
	private UpdatePricePOM upPom;
	private String URL;
	private ExtentTest log = null;
	private ExtentReports report = null;

	public UpdatePriceStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName());
		upPom = new UpdatePricePOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/updatePricetestreport.html");
		log = report.startTest("ExtentDemo");
	}

	@Given("^navigate to login page for updating the price of the product$")
	public void navigate_to_login_page_for_updating_the_price_of_the_product() throws Throwable {
		driver.get(URL);
	}

	@When("^clicked on login as vendor$")
	public void clicked_on_login_as_vendor() throws Throwable {
		upPom.clickVendorBtn();
	}

	@Then("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for updating the price of the product$")
	public void user_logged_in_using_as_user_and_password_as_for_updating_the_price_of_the_product(String username,
			String password) throws Throwable {
		upPom.sendUserName(username);
		upPom.sendPassword(password);
		upPom.clickLoginBtn();
	}

	@Then("^home page should be displayed for updating the price of the product$")
	public void home_page_should_be_displayed_for_updating_the_price_of_the_product() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", upPom.header());
	}

	@When("^update product price is selected$")
	public void update_product_price_is_selected() throws Throwable {
		upPom.updateProductPriceBtn();
	}

	@Then("^update price modal is displayed$")
	public void update_price_modal_is_displayed() throws Throwable {
		assertEquals("Select a Product", upPom.productModalPage());
	}

	@When("^product name here is selected as \"([^\"]*)\"$")
	public void product_name_here_is_selected_as(String productName) throws Throwable {
		upPom.selection(productName);
	}

	@When("^edit button is clicked to update the price$")
	public void edit_button_is_clicked_to_update_the_price() throws Throwable {
		upPom.editPriceBtn();
	}

	@Then("^update price and quantity modal is displayed$")
	public void update_price_and_quantity_modal_is_displayed() throws Throwable {
		assertEquals("Update Product Price and Quantity", upPom.updateProductModalPage());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
	}

	@When("^product base \"([^\"]*)\" is entered, \"([^\"]*)\" is entered$")
	public void product_base_is_entered_is_entered(String bp, String nqty) throws Throwable {
		Thread.sleep(1000);
		upPom.selection2(bp, nqty);
	}

	@When("^clicking on update button$")
	public void clicking_on_update_button() throws Throwable {
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
		upPom.updateProductPriceBtn();
	}

	@Then("^price is updated$")
	public void price_is_updated() throws Throwable {
		report.endTest(log);
		report.flush();
		driver.quit();
	}
}
