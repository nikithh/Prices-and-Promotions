/**
 * @author : Srinidhi
 * @see : This POM is for Create Store Step 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateStorePOM {

private WebDriver driver;
	
	public CreateStorePOM(WebDriver driver) {
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
	
	@FindBy(id="create-store-btn")
	private WebElement createStoreBtn;
	
	@FindBy(xpath ="//*[@id=\"store-form-container\"]/div[1]/h1")
	private WebElement storesModal;
	
	@FindBy(id ="zone")
	private WebElement zone;
	
	@FindBy(id ="cluster")
	private WebElement cluster;
	
	@FindBy(xpath ="//*[@id=\"zone\"]/option[2]")
	private WebElement zoneName;
	
	@FindBy(xpath ="//*[@id=\"cluster\"]/option[2]")
	private WebElement clusterName;
	
	@FindBy(id ="storeName")
	private WebElement storeName;
	
	@FindBy(id ="streetName")
	private WebElement streetName;
	
	@FindBy(id ="city")
	private WebElement city;
	
	@FindBy(id ="pin")
	private WebElement pin;
	
	@FindBy(id ="store-form-submit")
	private WebElement saveBtn;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div[2]")
	private WebElement successMsg;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div[2]")
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
	
	public void createStore() {
		this.createStoreBtn.click();
	}
	
	public String storesModal() {
		return this.storesModal.getText();
	}
	
	public void selection(String storeName) {
		this.zone.click();
		this.zoneName.click();
		this.cluster.click();
		this.clusterName.click();
		this.storeName.sendKeys(storeName);
	}
	
	public void selection1(String streetName,String city,String pin) {
		this.streetName.sendKeys(streetName);
		this.city.sendKeys(city);
		this.pin.sendKeys(pin);
	}
	
	public void selection2() {
		this.zone.click();
		this.zoneName.click();
		this.cluster.click();
		this.clusterName.click();
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
