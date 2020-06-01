/**
 * @author : Srinidhi

 * @see : This POM is for Price On Date Step 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PriceOnDatePOM {

private WebDriver driver;
	
	public PriceOnDatePOM(WebDriver driver) {
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
	
	@FindBy(id="price-dropdown")
	private WebElement priceDropDown;
	
	@FindBy(id="price-on-date-btn")
	private WebElement priceOnDateBtn;
	
	@FindBy(xpath ="//*[@id=\"root\"]/div/div/div/div/div[2]/form/div[1]/h1")
	private WebElement productModal;
	
	@FindBy(id = "product-list")
	private WebElement productList;

	@FindBy(id = "product-list-option-0")
	private WebElement productListJack;
	
	@FindBy(id ="selprods-submit")
	private WebElement assignBtn;
	
	@FindBy(xpath ="//*[@id=\"root\"]/div/div/div/div/div[2]/h4")
	private WebElement promotionModal;
	
	@FindBy(id="startDate-query")
	private WebElement startDate;
	
	@FindBy(id="endDate-query")
	private WebElement endDate;
	
	@FindBy(id="ProfitPercentage")
	private WebElement effectivePercentage;
	
	@FindBy(id="assign-cluster-submit")
	private WebElement saveBtn;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div[3]/div/div[2]")
	private WebElement successMsg;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div[2]/div/div/div[2]")
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
	
	public void priceDropdownBtn() {
		this.priceDropDown.click();
	}
	
	public void priceOnDateBtn() {
		this.priceOnDateBtn.click();
	}
	
	public String priceOnDateModal() {
		return this.productModal.getText();
	}
	
	public void product(String product) {
		this.productList.click();
		this.productList.sendKeys(product);
		this.productListJack.click();
	}
	
	public void clickAssignBtn() {
		this.assignBtn.click();
	}
	
	public String promotionModal() {
		return this.promotionModal.getText();
	}
	
	public void data(String startDate,String endDate,String percentage) {
		this.startDate.click();
		this.startDate.sendKeys(startDate);
		this.endDate.click();
		this.endDate.sendKeys(endDate);
		this.effectivePercentage.click();
		this.effectivePercentage.sendKeys(percentage);
	}
	
	public void saveBtn() {
		this.saveBtn.click();
	}
	
	public String successMsg() {
		return this.successMsg.getText();
	}
	
	public String failMsg() {
		return this.failMsg.getText();
	}
}