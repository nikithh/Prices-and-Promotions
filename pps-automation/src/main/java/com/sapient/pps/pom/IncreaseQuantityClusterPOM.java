/**
 * @author : Siva



 * @see : This POM is for Increase Quantity for Cluster Step
 * @since : May 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IncreaseQuantityClusterPOM {

	private WebDriver driver;
	public IncreaseQuantityClusterPOM(WebDriver driver) {
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
	
	@FindBy(id ="qty-dropdown")
	private WebElement qtyDropDownBtn;
	
	@FindBy(id ="add-qty-cluster-btn")
	private WebElement addQtyClusterBtn;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/form/div[1]/h1")
	private WebElement addQtyClusterHeader;
	
	@FindBy(id ="product-list")
	private WebElement productName;
	
	@FindBy(id ="product-list-option-0") //"//*[@id=\"product-list\"]/option[0]")
	private WebElement productNameOption;
	
	@FindBy(id ="zone")
	private WebElement zone;
	
	@FindBy(xpath ="//*[@id=\"zone\"]/option[2]")
	private WebElement zoneOption;
	
	@FindBy(id ="cluster")
	private WebElement cluster;
	
	@FindBy(xpath ="//*[@id=\"cluster\"]/option[2]")
	private WebElement clusterOption;
	
	@FindBy(id ="increase-cluster-qty")
	private WebElement goBtn;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div/h4")
	private WebElement addQtyClusterModelHeader;
	
	@FindBy(id ="clusterquantity")
	private WebElement clusterQuantity;
	
	@FindBy(id = "add-cluster-quantity")
	private WebElement assignQtyBtn;
	
	@FindBy(xpath ="//*[@id=\"root\"]/div/div/div/div/div[2]/h4")
	private WebElement addQtyClusterModelConfirmHeader;
	
	public void sendUserName(String username) {
		this.username.sendKeys(username);;
	}
	
	public void sendPassword(String password) {
		this.password.sendKeys(password); 
	}
	
	public void sendProductName(String cerelac) {
		this.productName.sendKeys(cerelac);
	}
	
	public void sendClusterQuantity(String quantity) {
		this.clusterQuantity.sendKeys(quantity);
	}
	
	public void clickLoginBtn() {
		this.loginBtn.click(); 
	}
	
	public void clickQtyDropDownBtn() {
		this.qtyDropDownBtn.click();
	}
	
	public void clickAddQtyClusterBtn() {
		this.addQtyClusterBtn.click();
	}
	
	public void productName() {
		this.productName.click();
	}
	
	public void productNameOption() {
		this.productNameOption.click();
	}
	
	public void zone() {
		this.zone.click();
	}
	
	public void zoneOption() {
		this.zoneOption.click();
	}
	
	public void cluster() {
		this.cluster.click();
	}
	
	public void clusterOption() {
		this.clusterOption.click();
	}
	
	public void goBtn() {
		this.goBtn.click();
	}
	
	public void assignQtyBtn() {
		this.assignQtyBtn.click();
	}
	
	public String header() {
		return this.header.getText();
	}
	
	public String addQtyClusterHeader() {
		return this.addQtyClusterHeader.getText();
	}
	
	public String addQtyClusterModelHeader() {
		return this.addQtyClusterModelHeader.getText();
	}
	
	public String addQtyClusterModelConfirmHeader() {
		return this.addQtyClusterModelConfirmHeader.getText();
	}
}
