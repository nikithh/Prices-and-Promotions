/**
 * @author : Srinidhi
 * @see : This class is for create cluster Step 
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
import com.sapient.pps.pom.CreateClusterPOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CreateClusterStep{
	
	private WebDriver driver;
	private CreateClusterPOM clusterPOM;
	private String URL;
	private ExtentTest log = null;
    private ExtentReports report = null ;

	public CreateClusterStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName()); 
		clusterPOM=new CreateClusterPOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/createclustertestreport.html");
	    log = report.startTest("ExtentDemo");
	}
	
	@Given("^navigate to retailer login page for creating cluster$")
	public void navigate_to_vendor_login_page_for_cluster() throws Throwable {
    	driver.get(URL);
	}

	@When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for creating cluster$")
	public void user_logged_in_using_as_user_and_password_as_for_cluster(String username, String password) throws Throwable {
		clusterPOM.sendUserName(username);
		clusterPOM.sendPassword(password);
		clusterPOM.clickLoginBtn();
	}

	@Then("^home page should be displayed for creating cluster$")
	public void home_page_should_be_displayed_for_cluster() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", clusterPOM.header());
	}
	
	@When("^location dropdown is selected$")
	public void location_dropdown_is_selected() throws Throwable {
	    clusterPOM.locationBtn();
	}

	@When("^create cluster is selected for creating cluster$")
	public void create_cluster_is_selected() throws Throwable {
		clusterPOM.createCluster();
	}

	@Then("^create cluster modal is displayed for creating cluster$")
	public void create_cluster_modal_is_displayed() throws Throwable {
		assertEquals("Create a cluster", clusterPOM.clustersModal());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@When("^zone value is selected as \"([^\"]*)\" and clustername is entered as \"([^\"]*)\" and taxrate is entered as \"([^\"]*)\"$")
	public void cluster_requirments_is_entered_as(String zoneName, String clusterName,String taxRate) throws Throwable {
		clusterPOM.selection(clusterName, taxRate);
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}
	
	@When("^save button is clicked for creating cluster$")
    public void save_button_is_clicked_for_cluster() throws Throwable {
		clusterPOM.clickSaveBtn();
    }
	
	@Then("^cluster creation failed should be displayed for creating cluster$")
	public void cluster_creation_failed_should_be_displayed() throws Throwable {
		assertEquals("Cluster creation failed. Please match the requirements",clusterPOM.failMsg());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
		driver.quit();
	}
	
	@Then("^cluster created succesfully should be displayed for creating cluster$")
	public void cluster_created_succesfully_should_be_displayed() throws Throwable {
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
		report.endTest(log);
		report.flush();
		driver.quit();
	} 

}
