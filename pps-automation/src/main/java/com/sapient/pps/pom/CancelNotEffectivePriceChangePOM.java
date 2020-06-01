/**
 * @author : Preeti Agrawal
 * @see : This class is for cancel not effective price change pom 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CancelNotEffectivePriceChangePOM {
	
	private WebDriver driver;

	public CancelNotEffectivePriceChangePOM(WebDriver driver) {
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
	
	@FindBy(id="price-dropdown")
	private WebElement priceBtn;
	
	@FindBy(id="cancel-not-effective-price-btn")
	private WebElement cancelPriceBtn;
	
	@FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div/div/h4")
	private WebElement priceChangeModal;
	
	@FindBy(id="assign-cluster-submit")
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
	
	public void priceBtn() {
		this.priceBtn.click();
	}

	public void clickCancelPrice() {
		this.cancelPriceBtn.click();
	}
	
	public String cancelPricePage() {
		return this.priceChangeModal.getText();
	}
	
	public void withdrawPrice() {
		this.withdrawBtn.click();
	}

}
