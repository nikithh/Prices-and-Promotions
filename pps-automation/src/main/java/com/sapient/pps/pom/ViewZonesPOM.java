/**
 * @author : Zubin
 * @see : This POM is for View Zones Step 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ViewZonesPOM {

	private WebDriver driver;
	public ViewZonesPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="username")
	private WebElement username;
	
	@FindBy(id="password")
	private WebElement password;
	
	@FindBy(id="retailer-login-btn")
	private WebElement loginBtn;
	
	@FindBy(id="view-zones-btn")
	private WebElement viewZonesBtn;
	
	@FindBy(id ="app-header")
	private WebElement header;
	
	@FindBy(id="location-dropdown")
	private WebElement locationBtn;
	
	@FindBy(xpath ="//*[@id=\"root\"]/div/div/div/div/h4")
	private WebElement zonesModal;
	
	public void sendUserName(String username) {
		this.username.sendKeys(username);
	}
	
	public void sendPassword(String password) {
		this.password.sendKeys(password); 
	}
	
	public void clickLoginBtn() {
		this.loginBtn.click(); 
	}
	
	public void locationBtn() {
		this.locationBtn.click();
	}
	
	public void viewZonesBtn() {
		this.viewZonesBtn.click();
	}
	
	public String header() {
		return this.header.getText();
	}
	
	public String zonesModal() {
		return this.zonesModal.getText();
	}
	
}
