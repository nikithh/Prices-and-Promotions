/**
 * @author : Zubin
 * @see : This class is for i18NChecking
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
import com.sapient.pps.pom.i18NCheckPOM;
import com.sapient.pps.screenshot.CaptureScreenShot;
import com.sapient.pps.utility.Driver;
import com.sapient.pps.utility.DriverFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class i18NCheckStep {
	private WebDriver driver;
	private i18NCheckPOM i18Pom;
	private String URL;
	private ExtentTest log = null;
    private ExtentReports report = null ;
	

	public i18NCheckStep() throws IOException {
		Properties prop = new Properties();
		FileInputStream inStream = new FileInputStream("./resources/config.properties");
		prop.load(inStream);
		URL = prop.getProperty("URL");
		driver = DriverFactory.getDriver(Driver.CHROME.getName());
		i18Pom = new i18NCheckPOM(driver);
		report = new ExtentReports("./target/cucumber-reports/Screenshots/i18Nchecktestreport.html");
	    log = report.startTest("ExtentDemo");

	}

	@Given("^accedez a la page de connexion du detaillant #navigate to retailer login page$")
	public void accedez_a_la_page_de_connexion_du_detaillant_navigate_to_retailer_login_page() throws Throwable {
		driver.get(URL);
	}

	@When("^lutilisateur clique sur le selecteur de langue #user clicks on language selector$")
	public void lutilisateur_clique_sur_le_selecteur_de_langue_user_clicks_on_language_selector() throws Throwable {
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
		i18Pom.flagBtn();
	}

	@Then("^selectionne le francais #selects french$")
	public void selectionne_le_francais_selects_french() throws Throwable {
		i18Pom.select_flag_FR();
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
		Thread.sleep(400);
	}

	@Then("^lutilisateur entre le nom dutilisateur comme \"([^\"]*)\" et mot de passe comme \"([^\"]*)\" lors de la conversion #user enters username as admin and password as admin upon conversion$")
	public void lutilisateur_entre_le_nom_dutilisateur_comme_et_mot_de_passe_comme_lors_de_la_conversion_user_enters_username_as_and_password_as_upon_conversion(
			String username, String password) throws Throwable {
		i18Pom.sendUserName(username);
		i18Pom.sendPassword(password);
	}

	@Then("^le bouton de connexion doit etre affiche en francais #login button should be displayed in french$")
	public void le_bouton_de_connexion_doit_etre_affiche_en_francais_login_button_should_be_displayed_in_french()
			throws Throwable {
		log.log(LogStatus.PASS, log.addScreenCapture(CaptureScreenShot.takeScreenshot(driver)) + "Test Passed" );
		assertEquals("LOGIN", i18Pom.loginBtn());
		report.endTest(log);
		report.flush();
		driver.quit();
	}
}
