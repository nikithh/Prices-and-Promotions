/**
 * @author : Preeti
 * @see : This step is for create admin
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
import com.sapient.pps.pom.CreateAdminPOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CreateAdminStep {
	
	private WebDriver driver;
	private CreateAdminPOM caPom;
	private String URL;
	private ExtentTest log = null;
    private ExtentReports report = null ;

	public CreateAdminStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName());
		caPom = new CreateAdminPOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/CreateAdminTestReport.html");
		log = report.startTest("ExtentDemo");
	}
	
	@Given("^navigate to retailer login page to create admin$")
	public void navigate_to_retailer_login_page_to_create_admin() throws Throwable {
	    driver.get(URL);
	}

	@When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" to create admin$")
	public void user_logged_in_using_as_user_and_password_as_to_create_admin(String username, String password) throws Throwable {
	    caPom.sendUserName(username);
	    caPom.sendPassword(password);
	    caPom.clickLoginBtn();
	}

	@Then("^home page should be displayed to create admin$")
	public void home_page_should_be_displayed_to_create_admin() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", caPom.header());
	}
	
	@When("^clicked on admin dropdown$")
	public void clicked_on_admin_dropdown() throws Throwable {
	    caPom.clickAdminBtn();
	}

	@When("^clicked on create admin$")
	public void clicked_on_create_admin() throws Throwable {
	    caPom.clickCreateAdminBtn();
	}

	@Then("^create admin modal is opened$")
	public void create_admin_modal_is_opened() throws Throwable {
		assertEquals("Create admin",caPom.createAdminModal());
	}

	@When("^username is entered as \"([^\"]*)\" and password is entered as \"([^\"]*)\"$")
	public void username_is_entered_as_and_password_is_entered_as(String user, String pwd) throws Throwable {
	    caPom.sendAdminUsername(user);
	    caPom.sendAdminPassword(pwd);
	    log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@When("^clicked on create admin button$")
	public void clicked_on_create_admin_button() throws Throwable {
	    caPom.createButton();
	}

	@Then("^admin is created$")
	public void admin_is_created() throws Throwable {
	   driver.quit(); 
	}

	@Then("^admin already exists is shown$")
	public void admin_already_exists_is_shown() throws Throwable {
		assertEquals("Create admin",caPom.createAdminModal());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}
}
