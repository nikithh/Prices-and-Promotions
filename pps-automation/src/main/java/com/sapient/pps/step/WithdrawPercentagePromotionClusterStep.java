/**
 * @author : Zubin
 * @see : This class is for Withdrawing Percentage Promotion Cluster Step 
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
import com.sapient.pps.pom.WithdrawPercentagePromotionClusterPOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class WithdrawPercentagePromotionClusterStep {
	private WebDriver driver;
	private WithdrawPercentagePromotionClusterPOM wcPom;
	private String URL;
	private ExtentTest log = null;
    private ExtentReports report = null ;

	public WithdrawPercentagePromotionClusterStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName());
		wcPom = new WithdrawPercentagePromotionClusterPOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/withdrawpercentagepromotionclustertestreport.html");
		log = report.startTest("ExtentDemo");
	}

	@Given("^navigate to login page to withdraw promotion in cluster level$")
	public void navigate_to_login_page_to_withdraw_promotion_in_cluster_level() throws Throwable {
		driver.get(URL);
	}

	@When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for withdrawing promotion at cluster level$")
	public void user_logged_in_using_as_user_and_password_as_for_withdrawing_promotion_at_cluster_level(String username,
			String password) throws Throwable {
		wcPom.sendUserName(username);
		wcPom.sendPassword(password);
		wcPom.clickLoginBtn();
	}

	@Then("^home page should be displayed for withdraw promotion at cluster level$")
	public void home_page_should_be_displayed_for_withdraw_promotion_at_cluster_level() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", wcPom.header());
	}
	
	@When("^promotion is clicked$")
	public void promotion_is_clicked() throws Throwable {
	    wcPom.promotionBtn();
	}

	@When("^withdraw percentage promotion from cluster level is selected$")
	public void withdraw_percentage_promotion_from_cluster_level_is_selected() throws Throwable {
		wcPom.clickWithdrawClusterBtn();
	}

	@Then("^withdraw percentage promotion modal is displayed$")
	public void withdraw_percentage_promotion_modal_is_displayed() throws Throwable {
		assertEquals("Withdraw Promotion from Cluster", wcPom.withdrawPromotionClusterModal());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@When("^zone name and cluster is selected and product name is inserted as \"([^\"]*)\"$")
	public void zone_name_and_cluster_is_selected_and_product_name_is_inserted_as(String productName) throws Throwable {
		wcPom.selection(productName);
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@Then("^check if all requirements are met for withdrawing percentage promotion from cluster level$")
	public void check_if_all_requirements_are_met_for_withdrawing_percentage_promotion_from_cluster_level()
			throws Throwable {
		assertEquals("Requirements", wcPom.reqCheck());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@When("^go button is clicked for withdrawing promotion in cluster level$")
	public void go_button_is_clicked_for_withdrawing_promotion_in_cluster_level() throws Throwable {
		wcPom.clickGoBtn();
	}
	
	@Then("^promotion at cluster modal is displayed$")
	public void promotion_at_cluster_modal_is_displayed() throws Throwable{
		assertEquals("Promotions in cluster level", wcPom.promotionClusterModal());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
		report.endTest(log);
		report.flush();
		driver.quit();
	}
}
