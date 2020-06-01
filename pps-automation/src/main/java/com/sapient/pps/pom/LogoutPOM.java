/**
 * @author : Zubin

 * @see : This POM is for Logout Step 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogoutPOM {

	private WebDriver driver;
	public LogoutPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="username")
	private WebElement username;
	
	@FindBy(id="password")
	private WebElement password;
	
	@FindBy(id="retailer-login-btn")
	private WebElement loginBtn;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div[1]/div/form/button")
	private WebElement loginBtnText;
	
	@FindBy(id ="app-header")
	private WebElement header;
	
	@FindBy(id ="logout-btn")
	private WebElement logoutBtn;
	
	public void sendUserName(String username) {
		this.username.sendKeys(username);
	}
	
	public void sendPassword(String password) {
		this.password.sendKeys(password); 
	}
	
	public void clickLoginBtn() {
		this.loginBtn.click(); 
	}
	
	public String header() {
		return this.header.getText();
	}
	
	public void clickLogoutBtn() {
		this.logoutBtn.click(); 
	}
	
	public String loginBtnText() {
		return this.loginBtnText.getText();
	}
}
