/**
 * @author : Preeti

 * @see : This class is for applying promotion in cluster 
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
import com.sapient.pps.pom.PromotionInClusterPOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PromotionInClusterStep {

	private WebDriver driver;
	private PromotionInClusterPOM pcPom;
	private String URL;
	private ExtentTest log = null;
    private ExtentReports report = null ;

	public PromotionInClusterStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName()); 
		pcPom=new PromotionInClusterPOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/promotioninclustertestreport.html");
		log = report.startTest("ExtentDemo");
	}
	
	@Given("^navigate to login page for promotion in cluster level$")
	public void navigate_to_login_page_for_promotion_in_cluster_level() throws Throwable {
        driver.get(URL);
	}
	
	@When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for cluster level promotion$")
	public void user_logged_in_using_as_user_and_password_as_for_cluster_level_promotion(String username, String password) throws Throwable {
		pcPom.sendUserName(username);
		  pcPom.sendPassword(password);
		  pcPom.clickLoginBtn();
	}

	@Then("^home page should be displayed for cluster level promotion$")
	public void home_page_should_be_displayed_for_cluster_level_promotion() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", pcPom.header());
	}
	
	@When("^promotion btn is clicked$")
	public void promotion_btn_is_clicked() throws Throwable {
	    pcPom.promotionBtn();
	}

	@When("^apply promotion in cluster level is selected$")
	public void apply_promotion_in_cluster_level_is_selected() throws Throwable {
	   pcPom.clickClusterPromotion();
	}

	@Then("^promotion product modal for cluster is displayed$")
	public void promotion_product_modal_for_cluster_is_displayed() throws Throwable {
		assertEquals("Select product name and cluster", pcPom.productModal());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@When("^product name is selected as \"([^\"]*)\", zoneName and clusterName$")
	public void product_name_is_selected_as_zoneName_and_clusterName(String productName) throws Throwable {
	    pcPom.selection(productName);
	    log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@When("^go button is clicked for applying promotion in cluster level$")
	public void go_button_is_clicked_for_applying_promotion_in_cluster_level() throws Throwable {
	    pcPom.clickGoBtn();
	}

	@Then("^Apply percentage promotion cluster modal is displayed$")
	public void apply_percentage_promotion_cluster_modal_is_displayed() throws Throwable {
	    assertEquals("Apply Percentage Promotion", pcPom.promotionModal());
	    log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@When("^selecting promotion percentage as \"([^\"]*)\",start date as \"([^\"]*)\", end date as \"([^\"]*)\"$")
	public void selecting_promotion_percentage_as_start_date_as_end_date_as(String promotionPercentage, String startDate, String endDate) throws Throwable {
	   pcPom.selection1(promotionPercentage, startDate, endDate); 
	   log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}
	
	@When("^Apply promotion to cluster button is clicked$")
	public void apply_promotion_to_cluster_button_is_clicked() throws Throwable {
		pcPom.clickClusterBtn();
	}

	@Then("^promotions in cluster level table is displayed$")
	public void promotions_in_cluster_level_table_is_displayed() throws Throwable {
	   assertEquals("Promotions in Cluster level", pcPom.clusterPromotionModal());
	   log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	   report.endTest(log);
	   report.flush();
	   driver.quit();
	}
}
