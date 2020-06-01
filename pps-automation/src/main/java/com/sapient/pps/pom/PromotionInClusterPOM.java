/**
 * @author : Preeti

 * @see : This class is for applying promotion in cluster level 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PromotionInClusterPOM {

	private WebDriver driver;

	public PromotionInClusterPOM(WebDriver driver) {
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

	@FindBy(id="apply-promotion-cluster-btn")
	private WebElement clusterPromotionBtn;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/form/div[1]/h1")
	private WebElement productModal;

	@FindBy(id = "product-list")
	private WebElement productList;

	@FindBy(id = "product-list-option-0")
	private WebElement productListJack;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/form/div[2]/div/div/div/div/button[2]")
	private WebElement productDropDown;

	@FindBy(id = "zone")
	private WebElement zone;

	@FindBy(xpath = "//*[@id=\"zone\"]/option[2]")
	private WebElement zoneName;

	@FindBy(id = "cluster")
	private WebElement cluster;

	@FindBy(xpath = "//*[@id=\"cluster\"]/option[2]")
	private WebElement clusterName;

	@FindBy(id = "apply-promotion-cluster-submit")
	private WebElement goBtn;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div/h4")
	private WebElement applyPromotionModal;

	@FindBy(id = "promotionPercentage")
	private WebElement proPercent;

	@FindBy(id = "startDate-in-range")
	private WebElement startDate;

	@FindBy(id = "endDate-in-range")
	private WebElement endDate;

	@FindBy(id = "apply-cluster-percentage")
	private WebElement cluPercentBtn;

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div/div/h6")
	private WebElement clusterPromotionModal;

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
		this.clusterPromotionBtn.click();
	}

	public String productModal() {
		return this.productModal.getText();
	}

	public void selection(String productName) {
		this.productList.click();
		this.productList.sendKeys(productName);
		this.productListJack.click();
		this.zone.click();
		this.zoneName.click();
		this.cluster.click();
		this.clusterName.click();
	}

	public void clickGoBtn() {
		this.goBtn.click();
	}

	public String promotionModal() {
		return this.applyPromotionModal.getText();
	}

	public void selection1(String proPercentage, String startDate, String endDate) {
		this.proPercent.click();
		this.proPercent.clear();
		this.proPercent.sendKeys(proPercentage);
		this.startDate.click();
		this.startDate.sendKeys(startDate);
		this.endDate.click();
		this.endDate.sendKeys(endDate);
	}

	public void clickClusterBtn() {
		this.cluPercentBtn.click();
	}

	public String clusterPromotionModal() {
		return this.clusterPromotionModal.getText();
	}
}
