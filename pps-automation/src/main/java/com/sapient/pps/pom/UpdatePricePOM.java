/**
 * @author : Preeti

 * @see : This class is for update price Step 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UpdatePricePOM {
	
private WebDriver driver;
	
	public UpdatePricePOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="reg-vendor")
	private WebElement vendorBtn;
	
	@FindBy(id="username-vendor")
	private WebElement vendorUsername;
	
	@FindBy(id="password-vendor")
	private WebElement vendorPassword;
	
	@FindBy(xpath="//*[@id=\"login-joint-form-vendor\"]/div/form/button[1]")
	private WebElement loginBtn;
	
	@FindBy(id ="app-header")
	private WebElement header;
	
	@FindBy(xpath="//*[@id=\"root\"]/div/div/nav/div[2]/div/div/div/ul/a[2]/li")
	private WebElement updatepriceBtn;
	
	@FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div[2]/form/div[1]/h1")
	private WebElement productModal;
	
	@FindBy(id = "product-list")
	private WebElement productList;

	@FindBy(id = "product-list-option-0")
	private WebElement productListJack;
	
	@FindBy(id="edit-price-btn")
	private WebElement editBtn;
	
	@FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div/div/div[1]/h1")
	private WebElement updateModal;
	
	@FindBy(id="BasePrice")
	private WebElement baseprice;
	
	@FindBy(id="newQuantity")
	private WebElement newqty;
	
	@FindBy(id="update-btn")
	private WebElement updateBtn;
	
	
	public void clickVendorBtn() {
		this.vendorBtn.click();
	}

	public void sendUserName(String username) {
		this.vendorUsername.clear();
		this.vendorUsername.sendKeys(username);
	}
	
	public void sendPassword(String password) {
		this.vendorPassword.clear();
		this.vendorPassword.sendKeys(password);
	}
	
	public void clickLoginBtn() {
		this.loginBtn.click();
	}
	
	public String header() {
		return this.header.getText();
	}
	
	public void updateProductPriceBtn(){
		this.updatepriceBtn.click();
	}
	
	public String productModalPage() {
		return this.productModal.getText();
	}
	
	public void selection(String productName) {
		this.productList.click();
		this.productList.sendKeys(productName);
		this.productListJack.click();
	}
	
	public void editPriceBtn() {
		this.editBtn.click();
	}
	
	public String updateProductModalPage() {
		return this.updateModal.getText();
	}
	
	public void selection2(String bp, String nqty) {
		this.baseprice.sendKeys(bp);
		this.newqty.sendKeys(nqty);
	}
	
	public void updatePriceBtn() {
		this.updateBtn.click();
	}

}
