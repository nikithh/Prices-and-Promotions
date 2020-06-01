/**
 * @author : Preeti
 * @see : This class is for Assign price to cluster/zone Step 
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
import com.sapient.pps.pom.AssignPricePOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AssignPriceStep {

	private WebDriver driver;
	private AssignPricePOM apPom;
	private String URL;
	private ExtentTest log = null;
    private ExtentReports report = null ;

	public AssignPriceStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName());
		apPom = new AssignPricePOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/AssignPriceTestReport.html");
		log = report.startTest("ExtentDemo");
	}

	@Given("^navigate to retailer login page for assigning price$")
	public void navigate_to_retailer_login_page_for_assigning_price() throws Throwable {
		driver.get(URL);
	}

	@When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for assigning price$")
	public void user_logged_in_using_as_user_and_password_as_for_assigning_price(String username, String password)
			throws Throwable {
		apPom.sendUserName(username);
		apPom.sendPassword(password);
		apPom.clickLoginBtn();
	}

	@Then("^home page should be displayed for assigning price$")
	public void home_page_should_be_displayed_for_assigning_price() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", apPom.header());
	}
	
	@When("^price dropdown button is selected$")
	public void price_dropdown_button_is_selected() throws Throwable {
	    apPom.priceBtn();
	}

	@When("^assign price to zone/cluster is selected$")
	public void assign_price_to_zone_cluster_is_selected() throws Throwable {
		apPom.clickClusterZoneBtn();
	}

	@Then("^select a product modal is displayed$")
	public void select_a_product_modal_is_displayed() throws Throwable {
		assertEquals("Select a product", apPom.productModalPage());
	}

	@When("^product name is entered as \"([^\"]*)\"$")
	public void product_name_is_entered_as(String productName) throws Throwable {
		apPom.selection(productName);
		Thread.sleep(1000);
	}

	@When("^assign price and cluster button is clicked$")
	public void assign_price_and_cluster_button_is_clicked() throws Throwable {
		apPom.assignPriceCluster();
	}

	@Then("^assign to cluster modal is displayed$")
	public void assign_to_cluster_modal_is_displayed() throws Throwable {
		assertEquals("Assign to Cluster", apPom.clusterModalPage());
	}

	@Then("^\"([^\"])\" letter is given, cluster is selected, \"([^\"])\" is given, \"([^\"]*)\" is entered$")
	public void letter_is_given_cluster_is_selected_is_given_is_entered(String cletter, String qty, String pp)
			throws Throwable {
		apPom.selection3(cletter, qty, pp);
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@When("^clicked on save button$")
	public void clicked_on_save_button() throws Throwable {
		apPom.clusterSaveBtn();
	}

	@Then("^cluster modal is displayed$")
	public void cluster_modal_is_displayed() throws Throwable {
		assertEquals("Assign to Cluster", apPom.assignClusterModalPage());
		driver.quit();
	}

	@When("^assign price and zone button is clicked$")
	public void assign_price_and_zone_button_is_clicked() throws Throwable {
		apPom.assignPriceZone();
	}

	@Then("^assign to zone modal is displayed$")
	public void assign_to_zone_modal_is_displayed() throws Throwable {
		assertEquals("Assign to Zone", apPom.zoneModalPage());
	}

	@Then("^zone is selected, \"([^\"]*)\" is given, \"([^\"]*)\" is entered$")
	public void zone_is_selected_is_given_is_entered(String qty, String pp) throws Throwable {
		apPom.selection2(qty, pp);
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}
	
	@Then("^cluster is selected, \"([^\"]*)\" is given and \"([^\"]*)\" is entered$")
	public void cluster_is_selected_is_given_and_is_entered(String cqty, String cpercent) throws Throwable {
		apPom.selection3(cqty, cpercent);
	}

	@When("^clicked on save$")
	public void clicked_on_save() throws Throwable {
		apPom.zoneSaveBtn();
	}

	@Then("^profit percentage modal is displayed$")
	public void profit_percentage_modal_is_displayed() throws Throwable {
		//assertEquals("Assign to Zone", apPom.assignZoneModalPage());
		assertEquals("Assign to Zone", apPom.zoneModalPage());
		report.endTest(log);
		report.flush();
		driver.quit();
	}

}