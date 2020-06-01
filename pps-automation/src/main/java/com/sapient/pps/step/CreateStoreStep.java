/**
 * @author : Srinidhi
 * @see : This class is for create store Step 
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
import com.sapient.pps.pom.CreateStorePOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CreateStoreStep {

	private WebDriver driver;
	private CreateStorePOM storePom;
	private String URL;
	private ExtentTest log = null;
    private ExtentReports report = null ;
	
	public CreateStoreStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName()); 
		storePom=new CreateStorePOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/createstoretestreport.html");
	    log = report.startTest("ExtentDemo");
	}

	@Given("^navigate to vendor login page for store$")
	public void navigate_to_vendor_login_page_for_store() throws Throwable {
        driver.get(URL);
	}

	@When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for store$")
	public void user_logged_in_using_as_user_and_password_as_for_store(String username, String password) throws Throwable {
		storePom.sendUserName(username);
        storePom.sendPassword(password);
        storePom.clickLoginBtn();
	}

	@Then("^home page should be displayed for store$")
	public void home_page_should_be_displayed_for_store() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", storePom.header());
	}
	
	@When("^location is clicked$")
	public void location_is_clicked() throws Throwable {
	    storePom.locationBtn();
	}

	@When("^create store is selected$")
	public void create_store_is_selected() throws Throwable {
		storePom.createStore();
	}

	@Then("^create store modal is displayed$")
	public void create_store_modal_is_displayed() throws Throwable {
		assertEquals("Create a Store", storePom.storesModal());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@When("^zone value is selected as zoneName , cluster name as clusterName, store name is selected as \"([^\"]*)\"$")
	public void zone_value_is_selected_as_zoneName_cluster_name_as_clusterName_store_name_is_selected_as(String storeName)
			throws Throwable {
		storePom.selection(storeName);
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@When("^street name value is selected as \"([^\"]*)\", city name as \"([^\"]*)\" and pin code as \"([^\"]*)\"$")
	public void street_name_value_is_selected_as_city_name_as_and_pin_code_as(String streetName, String city, String pin)
			throws Throwable {
		storePom.selection1(streetName, city, pin);
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@When("^save button is clicked for adding store$")
	public void save_button_is_clicked_for_adding_store() throws Throwable {
		storePom.clickSaveBtn();
	}

	@Then("^store created should be displayed$")
	public void store_created_should_be_displayed() throws Throwable {
		assertEquals("Store created successfully!", storePom.successMsg());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
		driver.quit();
	}

	@When("^zone value is selected as zoneName , cluster name as clusterName$")
	public void zone_value_is_selected_as_zoneName_cluster_name_as_clusterName() throws Throwable {
		storePom.selection2();
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@Then("^store creation failed should be displayed$")
	public void store_creation_failed_should_be_displayed() throws Throwable {
		assertEquals("Store creation failed. Please match the requirements", storePom.failMsg());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
		report.endTest(log);
		report.flush();
		driver.quit();
	}

}
