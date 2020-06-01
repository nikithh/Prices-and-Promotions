/**
 * @author : Preeti

 * @see : This POM is for Assign Price Step
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AssignPricePOM {

private WebDriver driver;
	
	public AssignPricePOM(WebDriver driver) {
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
	private WebElement priceDropdown;
	
	@FindBy(id="assign-price-zone-cluster-btn")
	private WebElement clusterZoneBtn;
	
	@FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div[2]/form/div[1]/h1")
	private WebElement productModal;
	
	@FindBy(id = "product-list")
	private WebElement productList;

	@FindBy(id = "product-list-option-0")
	private WebElement productListJack;
	
	@FindBy(id="selprods-submit")
	private WebElement assignClusterBtn;
	
	@FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div[2]/h4")
	private WebElement clusterModal;
	
	@FindBy(id="zonecluster")
	private WebElement cLetter;
	
	@FindBy(id="zoneclustername")
	private WebElement cluster;
	
	@FindBy(xpath="//*[@id=\"zoneclustername\"]/option[3]")
	private WebElement clusterName;
	
	@FindBy(id="clusterQuantity")
	private WebElement clusterQty;
	
	@FindBy(id="clusterProfitPercentage")
	private WebElement clusterPP;
	
	@FindBy(id="assign-cluster-submit")
	private WebElement saveClusterBtn;
	
	@FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div[2]/h4")
	private WebElement assignToClusterModal;
	
	@FindBy(id="assign-price-zone-submit")
	private WebElement assignZoneBtn;
	
	@FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div/h4")
	private WebElement zoneModal;
	
	@FindBy(id="zone")
	private WebElement zone;
	
	@FindBy(xpath="//*[@id=\"zone\"]/option[5]")
	private WebElement zoneName;
	
	@FindBy(id="zoneQuantity")
	private WebElement zoneQty;
	
	@FindBy(id="zoneProfitPercentage")
	private WebElement zonePP;
	
	@FindBy(id="assign-zone-submit")
	private WebElement saveBtn;
	
	@FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div[2]/h4")
	private WebElement assignToZoneModal;
	
	@FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div/form/div[4]/table/tbody/tr/td[3]/div/div/input")
	private WebElement clusterZoneQty;
	
	@FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div/form/div[4]/table/tbody/tr/td[4]/div/div/input")
	private WebElement clusterZonePP;
	
	@FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div/form/div[4]/table/tbody/tr/td[1]/div/label/span[1]/span[1]/input")
	private WebElement clusterZoneCheckBox;
	
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
		this.priceDropdown.click();
	}
	
	public void clickClusterZoneBtn() {
		this.clusterZoneBtn.click();
	}
	
	public String productModalPage() {
		return this.productModal.getText();
	}
	
	public void selection(String productName) {
		this.productList.click();
		this.productList.sendKeys(productName);
		this.productListJack.click();
	}
	
	public void assignPriceCluster() {
		this.assignClusterBtn.click();
	}
	
	public void assignPriceZone() {
		this.assignZoneBtn.click();
	}
	
	public String clusterModalPage() {
		return this.clusterModal.getText();
	}
	
	public String zoneModalPage() {
		return this.zoneModal.getText();
	}
	
	public void selection3(String letter, String quantity, String proPer) {
		this.cLetter.sendKeys(letter);
		this.cluster.click();
		this.clusterName.click();
		this.clusterQty.sendKeys(quantity);
		this.clusterPP.sendKeys(proPer);
	}
	
	public void selection2(String quantity, String proPer) {
		this.zone.click();
		this.zoneName.click();
		this.zoneQty.click();
		this.zoneQty.sendKeys(quantity);
		this.zonePP.click();;
		this.zonePP.sendKeys(proPer);
	}
	
	public void selection3(String cqty, String cpercent)
	{
		clusterZoneQty.click();
		clusterZoneQty.sendKeys(cqty);
		clusterZonePP.click();
		clusterZonePP.sendKeys(cpercent);
		clusterZoneCheckBox.click();
	}
	
	public void clusterSaveBtn() {
		this.saveClusterBtn.click();
	}
	
	public void zoneSaveBtn() {
		this.saveBtn.click();
	}
	
	public String assignClusterModalPage() {
		return this.assignToClusterModal.getText();
	}
	
	public String assignZoneModalPage() {
		return this.assignToZoneModal.getText();
	}
	
}