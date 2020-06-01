/**
 * @author : Siva



 * @see : This Step is for Increase Quantity for Zone
 * @since : May 2020
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
import com.sapient.pps.pom.IncreaseQuantityZonePOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class IncreaseQuantityZoneStep {
	
	private WebDriver driver; 
    private IncreaseQuantityZonePOM prPom; 
    private String URL;
    private ExtentTest log = null;
    private ExtentReports report = null ;
    
    public IncreaseQuantityZoneStep() throws IOException {
   	 Properties prop = new Properties(); 
   	 FileInputStream inStream = new FileInputStream("./resources/config.properties");
   	 prop.load(inStream);
     URL = prop.getProperty("URL"); 
     driver = DriverFactory.getDriver(Driver.CHROME.getName()); 
     prPom = new IncreaseQuantityZonePOM(driver);
     report = new ExtentReports("./target/cucumber-reports/Screenshots/addproducttestreport.html");
     log = report.startTest("ExtentDemo");
     }
    
	@Given("^navigate to retailer login page for updating quantity in zone$")
	public void access_to_retailer_login_page() throws Throwable {
        driver.get(URL); 
	}

	@When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for updating quantity at zone$")
	public void user_logged_in_using_as_user_and_password_as_for_updating_quantity_at_zone(String username, String password) throws Throwable {
		prPom.sendUserName(username);
		prPom.sendPassword(password);
		prPom.clickLoginBtn();
	}

	@Then("^home page should be displayed for updating quantity$")
	public void home_page_should_be_displayed_for_updating_quantity() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", prPom.header());
	}

	@When("^Quantity is selected from the sub-nav bar$")
	public void quantity_is_selected_from_the_sub_nav_bar() throws Throwable {
		prPom.clickQtyDropDownBtn();
	}

	@When("^Increase Quantity Zone is selected$")
	public void increase_Quantity_Zone_is_selected() throws Throwable {
	    prPom.clickAddQtyZoneBtn();
	}

	@Then("^Increase Quantity Zone Assignment Page is displayed$")
	public void increase_Quantity_Zone_Assignment_Page_is_displayed() throws Throwable {
		assertEquals("Select product name and zone", prPom.addQtyZoneHeader());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@When("^\"([^\"]*)\" is selected, zone is selected$")
	public void is_selected_zone_is_selected(String cerelac) throws Throwable {
	    prPom.productName();
	    prPom.sendProductName(cerelac);
	    prPom.productNameOption();
	    prPom.zone();
	    prPom.zoneOption();
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@When("^clicked on go button$")
	public void clicked_on_go_button() throws Throwable {
		prPom.goBtn();
	}

	@Then("^assign zone quantity is displayed$")
	public void assign_zone_quantity_is_displayed() throws Throwable {
	    assertEquals("Increase Quantity in Zone", prPom.addQtyZoneModelHeader());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@When("^\"([^\"]*)\" is entered$")
	public void is_entered(String quantity) throws Throwable {
	    prPom.sendZoneQuantity(quantity);
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@When("^clicked on assign button$")
	public void clicked_on_assign_button() throws Throwable {
	    prPom.assignQtyBtn();
	}

	@Then("^zone quantity model is displayed$")
	public void zone_quantity_model_is_displayed() throws Throwable {
		assertEquals("Assign to Zone", prPom.addQtyZoneModelConfirmHeader());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
		driver.quit();
	}

}
