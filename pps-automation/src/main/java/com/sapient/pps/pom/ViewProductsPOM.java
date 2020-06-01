package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ViewProductsPOM {

	private final WebDriver driver;

	public ViewProductsPOM(final WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "reg-vendor")
	private WebElement vendorBtn;

	@FindBy(id = "username-vendor")
	private WebElement vendorUsername;

	@FindBy(id = "password-vendor")
	private WebElement vendorPassword;

	@FindBy(id = "vendor-login-btn")
	private WebElement loginBtn;

	@FindBy(id = "app-header")
	private WebElement vendorModal;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/nav/div[2]/div/div/div/ul/a[3]/li")
	private WebElement viewProductBtn;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div/h1")
	private WebElement productModal;

	public void clickVendorBtn() {
		this.vendorBtn.click();
	}

	public void sendUserName(final String username) {
		this.vendorUsername.clear();
		this.vendorUsername.sendKeys(username);
	}

	public void sendPassword(final String password) {
		this.vendorPassword.clear();
		this.vendorPassword.sendKeys(password);
	}

	public void clickLoginBtn() {
		this.loginBtn.click();
	}

	public String vendorModal() {
		return this.vendorModal.getText();
	}

	public void clickViewProduct() {
		this.viewProductBtn.click();
	}

	public String getProductModal() {
		return this.productModal.getText();
	}
}
