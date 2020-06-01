/**
 * @author : Zubin

 * @see : This POM is for i18n Step 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class i18NCheckPOM {
	private WebDriver driver;
	public i18NCheckPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="username")
	private WebElement username;
	
	@FindBy(id="password")
	private WebElement password;
	
	@FindBy(id="select_flag_button")
	private WebElement flagBtn;
	
	@FindBy(id="select_flag_FR")
	private WebElement frFlagBtn;
	
	@FindBy(id="retailer-login-btn")
	private WebElement loginBtn;
	
	public void sendUserName(String username) {
		this.username.sendKeys(username);
	}
	
	public void sendPassword(String password) {
		this.password.sendKeys(password); 
	}
	
	public void flagBtn() {
		this.flagBtn.click();
	}
	
	public void select_flag_FR() {
		this.frFlagBtn.click(); 
	}
	
	public String loginBtn() {
		return this.loginBtn.getText(); 
	}
	
}
