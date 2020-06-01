/**
 * @author : Yatheesha

 * @see : This POM is for Vendor Login Step 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginVendorPOM {
	private WebDriver driver;
	
	public LoginVendorPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="reg-vendor")
	private WebElement vendorbtn;
	
	@FindBy(id="username-vendor")
	private WebElement username;
	
	@FindBy(id="password-vendor")
	private WebElement password;
	
	@FindBy(xpath = "//*[@id=\"login-joint-form-vendor\"]/div/form/button[1]")
	private WebElement loginBtn;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div/div/nav/div[2]/div/div/div/ul/a[1]/div/div[2]/span")
	private WebElement addproduct;
	
	@FindBy(id ="app-header")
	private WebElement header;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div[2]/div/div[2]")
	private WebElement wrongIp;
	public void clickVendorBtn() {
		this.vendorbtn.click(); 
	}
	
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
	
	public String wrongIp() {
		return this.wrongIp.getText();
	}
	
	public String addProduct() {
		return this.addproduct.getText();
	}

}
