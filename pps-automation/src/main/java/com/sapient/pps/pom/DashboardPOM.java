/**
 * @author : Preeti

 * @see : This POM is for Dashboard Step 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashboardPOM {
	
	private WebDriver driver;
	   
    public DashboardPOM(WebDriver driver) {
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
    
    @FindBy(id="dashboard-btn")
    private WebElement dashBtn;
    
    @FindBy(xpath="//*[@id=\"dashboard-box-container\"]/div[1]/div/div[1]/p")
    private WebElement dashBoardModal;
    
    @FindBy(xpath="//*[@id=\"dashboard-box-container\"]/div[1]/div/div[1]/div/a/button")
    private WebElement zoneBtn;
    
    @FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div[2]/form/div[1]/h1")
    private WebElement zoneModal;
    
    @FindBy(xpath="//*[@id=\"dashboard-box-container\"]/div[1]/div/div[2]/div/a/button")
    private WebElement clusterBtn;
    
    @FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div[2]/form/div[1]/h1")
    private WebElement clusterModal;
    
    @FindBy(xpath="//*[@id=\"dashboard-box-container\"]/div[1]/div/div[4]/div/a/button")
    private WebElement productsBtn;
    
    @FindBy(xpath="//*[@id=\"special-add-prods-help\"]")
    private WebElement productModal;
    
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
	
	public void clickDashboardBtn() {
		this.dashBtn.click();
	}
	
	public String dashModalPage() {
		return this.dashBoardModal.getText();
	}
	
	public void clickZoneBtn() {
		this.zoneBtn.click();
	}
	
	public String zoneModalPage() {
		return this.zoneModal.getText();
	}
	
	public void clickClusterBtn() {
		this.clusterBtn.click();
	}
	
	public String clusterModalPage() {
		return this.clusterModal.getText();
	}
	
	public void clickProductBtn() {
		this.productsBtn.click();
	}
	
	public String productModalPage() {
		return this.productModal.getText();
	}

}
