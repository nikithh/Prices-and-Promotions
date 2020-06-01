package com.sapient.pps.step;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sapient.pps.pom.ApprovePromotionsPOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ApprovePromotionsStep {

	
	private WebDriver driver; 
    private ApprovePromotionsPOM apPom; 
    private String URL;
    private ExtentTest log = null;
    private ExtentReports report = null ;
    
    public ApprovePromotionsStep() throws IOException {
   	 Properties prop = new Properties(); 
   	 FileInputStream inStream = new FileInputStream("./resources/config.properties");
   	 prop.load(inStream);
     URL = prop.getProperty("URL"); 
     driver = DriverFactory.getDriver(Driver.CHROME.getName()); 
     apPom = new ApprovePromotionsPOM(driver);
     report = new ExtentReports("./target/cucumber-reports/Screenshots/approvePromotionsTestReport.html");
     log = report.startTest("ExtentDemo");
     }
	
	@Given("^navigate to retailer login page for approving promotions$")
	public void navigate_to_retailer_login_page_for_approving_promotions() throws Throwable {
		driver.get(URL); 
	}

	@When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for approving promotions$")
	public void user_logged_in_using_as_user_and_password_as_for_approving_promotions(String username, String password) throws Throwable {
	   apPom.sendUserName(username);
	   apPom.sendPassword(password);
	   apPom.clickLoginBtn();
	}

	@Then("^home page should be displayed for approving promotions$")
	public void home_page_should_be_displayed_for_approving_promotions() throws Throwable {
	    assertEquals("PRICES AND PROMOTIONS", apPom.header());
	}

	@When("^admin dropdown button is clicked$")
	public void admin_dropdown_button_is_clicked() throws Throwable {
	    apPom.adminDropdownBtn();
	}
	
	@When("^approving promotions is selected$")
	public void approving_promotions_is_selected() throws Throwable {
	    apPom.approvePromBtn();
	}

	@Then("^select a product modal is displayed for approving promotions$")
	public void select_a_product_modal_is_displayed_for_approving_promotions() throws Throwable {
	    assertEquals("Select a Product", apPom.productModal());
	}

	@When("^product name is entered as \"([^\"]*)\" for approving promotions$")
	public void product_name_is_entered_as_for_approving_promotions(String productName) throws Throwable {
	    apPom.productSelection(productName);
	    Thread.sleep(1000);
	}

	@When("^approve promotions button is clicked$")
	public void approve_promotions_button_is_clicked() throws Throwable {
	    apPom.approveBtn();
	}

	@Then("^approve promotions modal is displayed$")
	public void approve_promotions_modal_is_displayed() throws Throwable {
	    assertEquals("Approve Promotions", apPom.approvePromModal());
	    log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	}

	@When("^approve green button is clicked$")
	public void approve_green_button_is_clicked() throws Throwable {
	    apPom.clickAcceptBtn();
	}

	@Then("^an alert is opened and when selected okay to approve$")
	public void an_alert_is_opened_and_when_selected_okay_to_approve() throws Throwable {
		driver.switchTo().alert().accept();
	}

	@Then("^promotion is approved$")
	public void promotion_is_approved() throws Throwable {
		report.endTest(log);
		report.flush();
		driver.quit();
	}

	@When("^reject red button is clicked$")
	public void reject_red_button_is_clicked() throws Throwable {
	    apPom.clickRejectBtn();
	}

	@Then("^an alert is opened and when selected okay to reject$")
	public void an_alert_is_opened_and_when_selected_okay_to_reject() throws Throwable {
//		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
	   driver.switchTo().alert().accept();
	}

	@Then("^promotion is rejected$")
	public void promotion_is_rejected() throws Throwable {
		report.endTest(log);
		report.flush();
		driver.quit();
	}
}
