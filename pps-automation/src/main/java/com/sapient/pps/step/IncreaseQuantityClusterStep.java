/**
 * @author : Siva



 * @see : This Step is for Increase Quantity for Cluster
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
import com.sapient.pps.pom.IncreaseQuantityClusterPOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class IncreaseQuantityClusterStep {

	private WebDriver driver; 
    private IncreaseQuantityClusterPOM prPom; 
    private String URL;
    private ExtentTest log = null;
    private ExtentReports report = null ;
    
    public IncreaseQuantityClusterStep() throws IOException {
      	 Properties prop = new Properties(); 
      	 FileInputStream inStream = new FileInputStream("./resources/config.properties");
      	 prop.load(inStream);
        URL = prop.getProperty("URL"); 
        driver = DriverFactory.getDriver(Driver.CHROME.getName()); 
        prPom = new IncreaseQuantityClusterPOM(driver);
        report = new ExtentReports("./target/cucumber-reports/Screenshots/addproducttestreport.html");
        log = report.startTest("ExtentDemo");
        } 
    
    @Given("^navigate to retailer login page for updating quantity in Cluster$")
    public void navigate_to_retailer_login_page_for_updating_quantity_in_Cluster() throws Throwable {
        driver.get(URL); 
    }

    @When("^user logged in using \"([^\"]*)\" as user and password as \"([^\"]*)\" for updating quantity at cluster$")
    public void user_logged_in_using_as_user_and_password_as_for_updating_quantity_at_cluster(String username, String password) throws Throwable {
    	prPom.sendUserName(username);
		prPom.sendPassword(password);
		prPom.clickLoginBtn();
    }

    @Then("^home page should be displayed for updating quantity cluster$")
    public void home_page_should_be_displayed_for_updating_quantity_cluster() throws Throwable {
		assertEquals("PRICES AND PROMOTIONS", prPom.header());
    }

    @When("^Quantity selected from the sub-nav bar$")
    public void quantity_selected_from_the_sub_nav_bar() throws Throwable {
		prPom.clickQtyDropDownBtn();
    }

    @When("^Increase Quantity Cluster is selected$")
    public void increase_Quantity_Cluster_is_selected() throws Throwable {
        prPom.clickAddQtyClusterBtn();
        Thread.sleep(500);
    }

    @Then("^Increase Quantity Cluster Assignment Page is displayed$")
    public void increase_Quantity_Cluster_Assignment_Page_is_displayed() throws Throwable {
		assertEquals("Select product name and cluster", prPom.addQtyClusterHeader());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
    }

    @When("^\"([^\"]*)\" is selected, zone is selected, cluster is selected$")
    public void is_selected_zone_is_selected_cluster_is_selected(String cerelac) throws Throwable {
    	prPom.productName();
	    prPom.sendProductName(cerelac);
	    prPom.productNameOption();
	    prPom.zone();
	    prPom.zoneOption();
	    prPom.cluster();
	    prPom.clusterOption();
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
    }

    @When("^clicked go button$")
    public void clicked_go_button() throws Throwable {
    	prPom.goBtn();
    }

    @Then("^assign cluster quantity is displayed$")
    public void assign_cluster_quantity_is_displayed() throws Throwable {
	    assertEquals("Increase Quantity in Cluster", prPom.addQtyClusterModelHeader());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
    }

    @When("^\"([^\"]*)\" is entered as text$")
    public void is_entered_as_text(String quantity) throws Throwable {
    	prPom.sendClusterQuantity(quantity);
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );

    }

    @When("^clicked  assign button$")
    public void clicked_assign_button() throws Throwable {
    	prPom.assignQtyBtn();
    }

    @Then("^cluster quantity model is displayed$")
    public void cluster_quantity_model_is_displayed() throws Throwable {
    	assertEquals("Assign to Cluster", prPom.addQtyClusterModelConfirmHeader());
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
		driver.quit();
    }
    
}
