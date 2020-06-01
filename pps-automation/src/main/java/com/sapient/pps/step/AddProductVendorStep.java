/**
 * @author : Yatheesha

 * @see : This is about vendor adding product 
 * @since : April 2020
 **/
package com.sapient.pps.step;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sapient.pps.pom.AddProductVendorPOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AddProductVendorStep {

	private WebDriver driver;
	private AddProductVendorPOM vendorPom;
	private String URL;
	private ExtentTest log = null;
	private ExtentReports report = null;

	public AddProductVendorStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName());
		vendorPom = new AddProductVendorPOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/AddproductVendortestreport.html");
		log = report.startTest("ExtentDemo");
	}

	@Given("^navigate to login page for vendor$")
	public void navigate_to_login_page_for_vendor() throws Throwable {
		driver.get(URL);
	}

	@When("^Clicked on login as vendor$")
	public void clicked_on_login_as_vendor() throws Throwable {
		vendorPom.clickVendorBtn();
	}

	@When("^user logged in using \"([^\"]*)\" as user input and password as \"([^\"]*)\" and login is clicked$")
	public void user_logged_in_using_as_user_input_and_password_as_and_login_is_clicked(String username,
			String password) throws Throwable {
		vendorPom.sendUserName(username);
		vendorPom.sendPassword(password);
		vendorPom.clickLoginBtn();
	}

	@Then("^Vendor home page should be displayed for adding product$")
	public void vendor_home_page_should_be_displayed_for_adding_product() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", vendorPom.vendorModal());
	}

	@When("^add a product is clicked$")
	public void add_a_product_is_clicked() throws Throwable {
		vendorPom.clickAddProduct();
	}

	@Then("^add a product modal is opened to add$")
	public void add_a_product_modal_is_opened_to_add() throws Throwable {
		assertEquals("AddProduct", vendorPom.addProductModal());
	}

	@When("^product name is given as \"([^\"]*)\"$")
	public void product_name_is_given_as(String productName) throws Throwable {
		vendorPom.selection1(productName);
	}

	@When("^product category is selected as alcohol product$")
	public void product_category_is_selected_as_alcohol_product() throws Throwable {
		vendorPom.category1();
		driver.quit();
	}

	@When("^product category is selected as baby product$")
	public void product_category_is_selected_as_baby_product() throws Throwable {
		vendorPom.category2();
		driver.quit();
	}

//	@When("^quantity is selected as \"([^\"]*)\",product base price is selected as \"([^\"]*)\"$")
//	public void quantity_is_selected_as_product_base_price_is_selected_as(String quantity, String basePrice)
//			throws Throwable {
//		vendorPom.selection2(quantity, basePrice);
//	}
//
//	@When("^abv is entered as \"([^\"]*)\", volume is entered as \"([^\"]*)\" and uom is selected$")
//	public void abv_is_entered_as_volume_is_entered_as_and_uom_is_selected(String abv, String volume) throws Throwable {
//		vendorPom.selection3(abv, volume);
//	}
//
//	@When("^uom is selected$")
//	public void uom_is_selected() throws Throwable {
//		//vendorPom.selectUom();
//		driver.quit();
//	}

//	@When("^product product description is selected as \"([^\"]*)\"$")
//	public void product_product_description_is_selected_as(String desc) throws Throwable {
//		//vendorPom.selection4(desc);
//		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
//	}
//
//	@When("^clicked on save button to save the product$")
//	public void clicked_on_save_button_to_save_the_product() throws Throwable {
//		vendorPom.clickSaveBtn();
//	}
//
//	@Then("^product is saved$")
//	public void product_is_saved() throws Throwable {
//		report.endTest(log);
//		report.flush();
//		driver.quit();
//	}
	
	
}