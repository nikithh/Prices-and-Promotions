/**
 * @author : Zubin
 * @see : This class is for Add Product To Store Step 
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
import com.sapient.pps.pom.AddProductToStoreRetailerPOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AddProductToStoreRetailerStep {
	
	private WebDriver driver; 
    private AddProductToStoreRetailerPOM prPom; 
    private String URL;
    private ExtentTest log = null;
    private ExtentReports report = null ;
    
    public AddProductToStoreRetailerStep() throws IOException {
   	 Properties prop = new Properties(); 
   	 FileInputStream inStream = new FileInputStream("./resources/config.properties");
   	 prop.load(inStream);
     URL = prop.getProperty("URL"); 
     driver = DriverFactory.getDriver(Driver.CHROME.getName()); 
     prPom = new AddProductToStoreRetailerPOM(driver);
     report = new ExtentReports("./target/cucumber-reports/Screenshots/addproducttestreport.html");
     log = report.startTest("ExtentDemo");
     }
    
	@Given("^access to retailer login page$")
	public void access_to_retailer_login_page() throws Throwable {
        driver.get(URL); 
	}

	@When("^user logged in as \"([^\"]*)\" as user and password as \"([^\"]*)\"$")
	public void user_logged_in_as_as_user_and_password_as(String username, String password) throws Throwable {
		prPom.sendUserName(username);
		prPom.sendPassword(password);
		prPom.clickLoginBtn();
	}

	@Then("^home page should be shown$")
	public void home_page_should_be_shown() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", prPom.header());
	}
	@When("^add Product To Store is selected$")
	public void Add_Product_To_Store_is_selected() throws Throwable {
		prPom.addProductsBtn();
	}

	@Then("^add Product To Store modal is displayed$")
	public void Add_Product_To_Store_modal_is_displayed() throws Throwable {
		assertEquals("Add Products to the Store", prPom.addProductHeader());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@Then("^zone value, cluster value and store value is selected$")
	public void Zone_value_Cluster_value_and_Store_value_is_selected() throws Throwable {
		prPom.zone();
		prPom.zoneOption();
		prPom.cluster();
		prPom.clusterOption();
		prPom.store();
		prPom.storeOption();
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}
	@Then("^Add Products button is clicked$")
    public void add_group_save_button_is_clicked() throws Throwable {
        prPom.clickAddProducts();
    }
	
	@Then("^Add Products page should be displayed$")
	public void add_Products_page_should_be_displayed() throws Throwable {
		assertEquals("Add Products to the Store", prPom.productTable());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}
	
	@When("^enter category is selected$")
	public void Enter_Category_is_selected() throws Throwable {
		prPom.category();
	}
	
	@Then("^product type is selected$")
	public void Product_type_is_selected() throws Throwable {
		prPom.categoryOption();
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@When("^select checkbox is ticked$")
	public void Select_Checkbox_is_ticked() throws Throwable {
		prPom.productCheckbox();
	}
	
	@Then("^quantity value is entered as \"([^\"]*)\"")
	public void Quantity_value_is_entered_as(String quantity) throws Throwable {
		prPom.quantityBox(quantity);
	}
	
	@Then("^next Add Products button is clicked$")
    public void next_Add_Products_button_is_clicked() throws Throwable {
		prPom.submitProduct();
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
		report.endTest(log);
		report.flush();
        driver.quit();
	}
}
