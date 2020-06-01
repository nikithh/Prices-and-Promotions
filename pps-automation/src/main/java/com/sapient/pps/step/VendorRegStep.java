/**
 * @author : Yatheesha

 * @see : This class is for vendor registration step
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
import com.sapient.pps.pom.VendorRegPOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class VendorRegStep {
	private WebDriver driver;
	private VendorRegPOM rPom;
	private String URL;
	private ExtentTest log = null;
	private ExtentReports report = null;

	public VendorRegStep() throws IOException {

		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName());
		rPom = new VendorRegPOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/VendorRegTestReport.html");
		log = report.startTest("ExtentDemo");
	}

	@Given("^navigate to vendor reg form$")
	public void navigate_to_vendor_reg_form() throws Throwable {
		driver.get(URL);
		rPom.clickVendorBtn();
		rPom.clickregBtn();
	}

	@When("^email value is entered as \"([^\"]*)\" and password as \"([^\"]*)\" , confirm password as \"([^\"]*)\",company name as \"([^\"]*)\",company type as \"([^\"]*)\" product Catogory as \"([^\"]*)\"$")
	public void email_value_is_entered_as_and_password_as_confirm_password_as_company_name_as_company_type_as_product_Catogory_as(
			String email, String pwd, String pwd1, String cName, String cType, String pType) throws Throwable {

		rPom.sendEmail(email);
		rPom.sendPassword(pwd);
		rPom.sendConfirmPassword(pwd1);
		rPom.sendCompanyName(cName);
		rPom.sendCompanyType(cType);
		rPom.sendProductType(pType);
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed");
		rPom.clicSighUpBtn();
	}

	@Then("^validate the outcomes$")
	public void validate_the_outcomes() throws Throwable {
		assertEquals("NOT A VENDOR? SIGN UP", rPom.getButtonLable());
		report.endTest(log);
		report.flush();
		driver.quit();
	}
}
