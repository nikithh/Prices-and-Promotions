/**
 * @author : Srinidhi
 * @see : This POM is for Cancel Effective Price Change Step 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CancelEffectivePriceChangePOM {

	private WebDriver driver;

	public CancelEffectivePriceChangePOM(WebDriver driver) {
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
	
	@FindBy(id="cancel-effective-price-btn")
	private WebElement cancelEffectivePriceChangeBtn;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div/div/h4")
	private WebElement cancelEffectivePriceChangeModal;

	@FindBy(id = "assign-cluster-submit")
	private WebElement cepcButton;

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

	public void priceBtn() {
		this.priceBtn.click();
	}
	
	public void cancelEffectivePriceChangeBtn() {
		this.cancelEffectivePriceChangeBtn.click();
	}

	public String cancelEffectivePriceChangeModal() {
		return this.cancelEffectivePriceChangeModal.getText();
	}

	public void cepcButton() {
		this.cepcButton.click();
	}
}
