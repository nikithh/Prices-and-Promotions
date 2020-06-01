/**
 * @author : Preeti

 * @see : This class is for withdrawing promotion from zone  
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WithdrawPromotionZonePOM {
	
	private WebDriver driver;

	public WithdrawPromotionZonePOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "username")
	private WebElement username;

	@FindBy(id = "password")
	private WebElement password;

	@FindBy(id="retailer-login-btn")
	private WebElement loginBtn;

	@FindBy(id = "app-header")
	private WebElement header;
	
	@FindBy(id="promotion-dropdown")
	private WebElement promotionBtn;
	
	@FindBy(id="withdraw-zone-promotion-btn")
	private WebElement withdrawPromotionBtn;
	
	@FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div[2]/form/div[1]/h1")
	private WebElement zoneModal;
	
	@FindBy(id="product-list")
	private WebElement productList;
	
	@FindBy(id="product-list-option-0")
	private WebElement productListToy;
	
	@FindBy(id="zone")
	private WebElement zone;
	
	@FindBy(xpath = "//*[@id=\"zone\"]/option[2]")
	private WebElement zoneName;
	
	@FindBy(id="cluster-form-submit")
	private WebElement goBtn;
	
	@FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div/div/h5")
	private WebElement withdrawPromotionModal;
	
	@FindBy(xpath="//*[@id=\"row2\"]/td[5]/h6/button")
	private WebElement withdrawBtn;
	
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

	public void promotionBtn() {
		this.promotionBtn.click();
	}
	
	public void clickClusterPromotion() {
		this.withdrawPromotionBtn.click();
	}

	public String productModal() {
		return this.zoneModal.getText();
	}

	public void selection(String productName) {
		this.productList.click();
		this.productList.sendKeys(productName);
		this.productListToy.click();
		this.zone.click();
		this.zoneName.click();
	}

	public void clickGoBtn() {
		this.goBtn.click();
	}

	public String promotionModal() {
		return this.withdrawPromotionModal.getText();
	}
	
	public void withdrawButton() {
		this.withdrawBtn.click();
	}
}
