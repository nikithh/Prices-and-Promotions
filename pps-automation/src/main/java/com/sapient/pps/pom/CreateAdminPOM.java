/**
 * @author : Preeti
 * @see : This POM is for Create admin
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateAdminPOM {
	
	private WebDriver driver;
	
	public CreateAdminPOM(WebDriver driver) {
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
	
	@FindBy(id ="admin-dropdown")
	private WebElement adminDropDown;
	
	@FindBy(id ="create-admin-btn")
	private WebElement createAdminBtn;
	
	@FindBy(xpath ="//*[@id=\"root\"]/div/div/div/div/div[2]/form/div[1]/h1")
	private WebElement createModal;
	
	@FindBy(id ="admin-input-username")
	private WebElement adminName;
	
	@FindBy(id ="admin-input-password")
	private WebElement adminPassword;
	
	@FindBy(id ="admin-submit")
	private WebElement createBtn;
	
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
	
	public void clickAdminBtn() {
		this.adminDropDown.click();
	}
	
	public void clickCreateAdminBtn() {
		this.createAdminBtn.click();
	}
	
	public String createAdminModal() {
		return this.createModal.getText();
	}
	
	public void sendAdminUsername(String user) {
		this.adminName.clear();
		this.adminName.sendKeys(user);
	}
	
	public void sendAdminPassword(String pwd) {
		this.adminPassword.clear();
		this.adminPassword.sendKeys(pwd);
	}
	
	public void createButton() {
		this.createBtn.click();
	}

}
