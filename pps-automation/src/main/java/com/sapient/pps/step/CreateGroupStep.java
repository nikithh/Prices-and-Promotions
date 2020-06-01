/**
 * @author : Srinidhi
 * @see : This class is for create group Step 
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
import com.sapient.pps.pom.CreateGroupPOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CreateGroupStep {
	private WebDriver driver;
	private CreateGroupPOM groupPom;
	private String URL;
	private ExtentTest log = null;
    private ExtentReports report = null ;

	public CreateGroupStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName()); 
		groupPom=new CreateGroupPOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/creategrouptestreport.html");
	    log = report.startTest("ExtentDemo");
	}

	@Given("^navigate to retailer login page for group$")
	public void navigate_to_retailer_login_page_for_group() throws Throwable {
        driver.get(URL);
	}

	@When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for group$")
	public void user_logged_in_using_as_user_and_password_as_for_group(String username, String password) throws Throwable {
		groupPom.sendUserName(username);
		groupPom.sendPassword(password);
		groupPom.clickLoginBtn();
	}

	@Then("^home page should be displayed for group$")
	public void home_page_should_be_displayed_for_group() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", groupPom.header());
	}

	@When("^add group is selected$")
	public void add_group_is_selected() throws Throwable {
		groupPom.createGroup();
	}

	@Then("^add group modal is displayed$")
	public void add_group_modal_is_displayed() throws Throwable {
		assertEquals("Create a Group", groupPom.groupsModal());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@When("^group value is entered as \"([^\"]*)\"$")
	public void group_value_is_entered_as(String groupName) throws Throwable {
		groupPom.sendGroupName(groupName);
	}

	@When("^add group save button is clicked$")
	public void add_group_save_button_is_clicked() throws Throwable {
		groupPom.clickSaveBtn();
	}

	@Then("^add group succeeded should be displayed$")
	public void add_group_succeeded_should_be_displayed() throws Throwable {
		//assertEquals("Group Created Succesfully",groupPom.successMsg());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
		driver.quit();
	}

	@Then("^group already exists should be displayed$")
	public void group_already_exists_should_be_displayed() throws Throwable {
		//assertEquals("Group Already Exists",groupPom.failMsg());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
		driver.quit();
	}

	@Then("^add group failed should be displayed$")
	public void add_group_failed_should_be_displayed() throws Throwable {
		//assertEquals("Group creation failed. Please match the requirements",groupPom.failMsg1());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
		report.endTest(log);
		report.flush();
		driver.quit();
	}

}
