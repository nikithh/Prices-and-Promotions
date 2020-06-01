/**
 * @author : Zubin
 * @see : This POM is for View Clusters Step 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ViewClustersPOM {

	private WebDriver driver;
	public ViewClustersPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="username")
	private WebElement username;
	
	@FindBy(id="password")
	private WebElement password;
	
	@FindBy(id="retailer-login-btn")
	private WebElement loginBtn;
	
	@FindBy(id="view-clusters-btn")
	private WebElement viewClustersBtn;
	
	@FindBy(id="location-dropdown")
	private WebElement locationBtn;
	
	@FindBy(id ="app-header")
	private WebElement header;
	
	@FindBy(xpath ="//*[@id=\"root\"]/div/div/div/div/h4")
	private WebElement clustersModal;
	
	@FindBy(id ="logout-btn")
	private WebElement logoutBtn;
	
	
	public void sendUserName(String username) {
		this.username.sendKeys(username);;
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
	
	public void viewClustersBtn() {
		this.viewClustersBtn.click();
	}
	
	public String header() {
		return this.header.getText();
	}
	public String clustersModal() {
		return this.clustersModal.getText();
	}
	
	public void clickLogoutBtn() {
		this.logoutBtn.click(); 
	}
	
	public String loginTxt() {
		return this.loginBtn.getText();
	}
}
