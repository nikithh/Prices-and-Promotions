/**
 * @author : Zubin
 * @see : This POM is for Create Zone Step 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateZonePOM {

	private WebDriver driver;
	
	public CreateZonePOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="username")
	private WebElement username;
	
	@FindBy(id="password")
	private WebElement password;
	
	@FindBy(id="retailer-login-btn")
	private WebElement loginBtn;
	
	@FindBy(id ="app-header")
	private WebElement header;
	
	@FindBy(id="location-dropdown")
	private WebElement locationBtn;
	
	@FindBy(id="create-zone-btn")
	private WebElement createZoneBtn;
	
	@FindBy(xpath ="//*[@id=\"root\"]/div/div/div/div/div[2]/form/div[1]/h1")
	private WebElement zonesModal;
	
	@FindBy(id ="zoneName")
	private WebElement zoneName;
	
	@FindBy(id ="liquorPricePerUnit")
	private WebElement lppu;
	
	@FindBy(id ="zone-form-submit")
	private WebElement saveBtn;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div[3]/div/div[2]")
	private WebElement successMsg;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div[2]/div")
	private WebElement failMsg;
	
	public void sendUserName(String username) {
		this.username.clear();
		this.username.sendKeys(username);
	}
	
	public void sendPassword(String password) {
		this.password.clear(); 
		this.password.sendKeys(password); 
	}
	
	public void clickLoginBtn() {
		this.loginBtn.click(); 
	}
	
	public String header() {
		return this.header.getText();
	}
	
	public void locationBtn() {
		this.locationBtn.click();
	}
	
	public void createzone() {
		this.createZoneBtn.click();
	}
	
	public String zonesModal() {
		return this.zonesModal.getText();
	}
	
	public void sendZoneName(String zoneName) {
		this.zoneName.clear();
		this.zoneName.sendKeys(zoneName);
	}
	
	public void sendPpu(String lppu) {
		this.lppu.clear(); 
		this.lppu.sendKeys(lppu); 
	}
	
	public void clickSaveBtn() {
		this.saveBtn.click();
	}
	
	public String successMsg() {
		return this.successMsg.getText();
	}
	
	public String failMsg() {
		return this.failMsg.getText();
	}	
}
