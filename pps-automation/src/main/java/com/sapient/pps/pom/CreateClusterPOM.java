/**
 * @author : Srinidhi
 * @see : This POM is for Create Cluster Step 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateClusterPOM {

private WebDriver driver;
	
	public CreateClusterPOM(WebDriver driver) {
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
	
	@FindBy(id="create-cluster-btn")
	private WebElement createClusterBtn;
	
	@FindBy(xpath ="//*[@id=\"root\"]/div/div/div/div/div[2]/form/div[1]/h1")
	private WebElement clustersModal;
	
	@FindBy(id ="zone")
	private WebElement zone;
	
	@FindBy(xpath ="//*[@id=\"zone\"]/option[6]")
	private WebElement zoneName;
	
	@FindBy(id ="clusterName")
	private WebElement clusterName;
	
	@FindBy(id="taxRate")
	private WebElement taxRate;
	
	@FindBy(id ="cluster-form-submit")
	private WebElement saveBtn;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div[2]/div/div[2]")
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
	
	public void createCluster() {
		this.createClusterBtn.click();
	}
	
	public String clustersModal() {
		return this.clustersModal.getText();
	}
	
	public void selection(String clusterName,String taxRate) {
		this.zone.click();
		this.zoneName.click();
		this.clusterName.sendKeys(clusterName);
		this.taxRate.sendKeys(taxRate);
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
