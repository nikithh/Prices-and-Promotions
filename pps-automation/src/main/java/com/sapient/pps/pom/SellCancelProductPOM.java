/**
 * @author : Srinidhi

 * @see : This class is for sell cancel product Step 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SellCancelProductPOM {

	private WebDriver driver;

	public SellCancelProductPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "username")
	private WebElement username;

	@FindBy(id = "password")
	private WebElement password;

	@FindBy(id = "retailer-login-btn")
	private WebElement loginBtn;

	@FindBy(id = "app-header")
	private WebElement header;
	
	@FindBy(id="price-dropdown")
	private WebElement priceBtn;

	@FindBy(id = "sell-cancel-fixed-price-btn")
	private WebElement sellCancelBtn;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/form/div[1]/h1")
	private WebElement productModal;

	@FindBy(id = "product-list")
	private WebElement productList;

	@FindBy(id = "product-list-option-0")
	private WebElement productValue;

	@FindBy(id = "cluster-form-submit")
	private WebElement goBtn;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div/div/h4")
	private WebElement prodDescModal;

	@FindBy(id = "sell-price-change-btn")
	private WebElement sellBtn;

	@FindBy(id = "cancel-price-change-btn")
	private WebElement cancelBtn;

	@FindBy(xpath = "")
	private WebElement soldSuccessMsg;

	@FindBy(xpath = "")
	private WebElement cancelSuccessMsg;

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

	public void clickPriceBtn() {
		this.priceBtn.click();
	}
	
	public void clickSellCancelBtn() {
		this.sellCancelBtn.click();
	}

	public String productModal() {
		return this.productModal.getText();
	}

	public void product(String product) {
		this.productList.click();
		this.productList.sendKeys(product);
		this.productValue.click();
	}

	public void clickGoBtn() {
		this.goBtn.click();
	}

	public String prodDescModal() {
		return this.prodDescModal.getText();
	}

	public void clickSellBtn() {
		this.sellBtn.click();
	}

	public void clickCancelBtn() {
		this.cancelBtn.click();
	}

	public String soldSuccessMsg() {
		return this.soldSuccessMsg.getText();
	}

	public String cancelSucessMsg() {
		return this.cancelSuccessMsg.getText();
	}

}