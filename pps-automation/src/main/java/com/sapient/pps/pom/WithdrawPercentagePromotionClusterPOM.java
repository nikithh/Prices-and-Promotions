/**
 * @author : Zubin
 * @see : This POM is for Withdrawing Percentage Promotion Cluster Step 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WithdrawPercentagePromotionClusterPOM {

	private WebDriver driver;

	public WithdrawPercentagePromotionClusterPOM(WebDriver driver) {
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

	@FindBy(id="withdraw-cluster-promotion-btn")
	private WebElement WithdrawPromotionBtn;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/form/div[1]/h1")
	private WebElement withdrawPromotionClusterModal;

	@FindBy(id = "product-list")
	private WebElement productList;

	@FindBy(id = "product-list-option-0")
	private WebElement productListCorona;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/h3")
	private WebElement reqCheck;

	@FindBy(id = "zone")
	private WebElement zoneList;

	@FindBy(xpath = "//*[@id=\"zone\"]/option[2]")
	private WebElement zoneSelect;

	@FindBy(id = "cluster")
	private WebElement clusterList;

	@FindBy(xpath = "//*[@id=\"cluster\"]/option[2]")
	private WebElement clusterSelect;

	@FindBy(id = "apply-promotion-cluster-submit")
	private WebElement goBtn;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div/div/h6")
	private WebElement promotionClusterModal;


	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div/div/h6")
	private WebElement tableModal;

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
	
	public void promotionBtn() {
		this.promotionBtn.click();
	}

	public void clickWithdrawClusterBtn() {
		this.WithdrawPromotionBtn.click();
	}

	public String withdrawPromotionClusterModal() {
		return this.withdrawPromotionClusterModal.getText();
	}

	public void selection(String productName) {
		this.productList.click();
		this.productList.sendKeys(productName);
		this.productListCorona.click();
		this.zoneList.click();
		this.zoneSelect.click();
		this.clusterList.click();
		this.clusterSelect.click();
	}

	public String reqCheck() {
		return this.reqCheck.getText();
	}

	public void clickGoBtn() {
		this.goBtn.click();
	}

	public String promotionClusterModal() {
		return this.promotionClusterModal.getText();
	}

//	public void selection1(String promotionPercentage, String startDate, String endDate) {
//		this.promotionPercentage.click();
//		this.promotionPercentage.sendKeys(promotionPercentage);
//		this.startDate.click();
//		this.startDate.sendKeys(startDate);
//		this.endDate.click();
//		this.endDate.sendKeys(endDate);
//	}
//
//	public void applyPromotionBtn() {
//		this.applyPromotionBtn.click();
//	}

	public String tableModal() {
		return this.tableModal.getText();
	}
}
