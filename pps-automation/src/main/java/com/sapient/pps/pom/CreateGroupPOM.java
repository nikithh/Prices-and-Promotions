/**
 * @author : Srinidhi
 * @see : This POM is for Create Group Step 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateGroupPOM {

private WebDriver driver;
	
	public CreateGroupPOM(WebDriver driver) {
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
	
	@FindBy(id="create-group-btn")
	private WebElement createGroupBtn;
	
	@FindBy(xpath ="//*[@id=\"root\"]/div/div/div/div/div[2]/form/div[1]/h1")
	private WebElement groupsModal;
	
	@FindBy(id ="group-input")
	private WebElement groupName;
	
	@FindBy(id ="group-submit")
	private WebElement saveBtn;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div[3]/div")
	private WebElement successMsg;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div[3]/div")
	private WebElement failMsg;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div[2]/div/div/div[2]")
	private WebElement failMsg1;
	
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
	
	public void createGroup() {
		this.createGroupBtn.click();
	}
	
	public String groupsModal() {
		return this.groupsModal.getText();
	}
	
	public void sendGroupName(String groupName) {
		this.groupName.clear();
		this.groupName.sendKeys(groupName);
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
	
	public String failMsg1() {
		return this.failMsg1.getText();
	}
	
}
