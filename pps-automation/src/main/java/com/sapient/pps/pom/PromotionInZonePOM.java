/**
 * @author : Zubin
 * @see : This POM is for Promotion In Zone
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PromotionInZonePOM {

	private WebDriver driver;
	   
    public PromotionInZonePOM(WebDriver driver) {
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
    
    @FindBy(id="promotion-dropdown")
    private WebElement promotionBtn;
    
    @FindBy(id="apply-promotion-zone-btn")
    private WebElement promotionZoneBtn;
    
    @FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div[2]/form/div[1]/h1")
    private WebElement productModal;
    
    @FindBy(id="product-list")
    private WebElement productList;
    
    @FindBy(id="product-list-option-0")
    private WebElement productListJack;
    
    @FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div[1]/div/h3")
    private WebElement reqCheck;
    
    @FindBy(id="zone")
    private WebElement zoneList;
    
   @FindBy(xpath="//*[@id=\"zone\"]/option[2]")
    private WebElement zoneSelect;
    
    @FindBy(id="apply-promotion-zone-submit")
    private WebElement goBtn;
    
    @FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div[2]/div/h4")
    private WebElement promotionModal;
    
    @FindBy(id="promotionPercentage")
    private WebElement promotionPercentage;
    
    @FindBy(id="startDate-in-range")
    private WebElement startDate;
    
    @FindBy(id="endDate-in-range")
    private WebElement endDate;
    
    @FindBy(id="apply-zone-percentage")
    private WebElement applyPromotionBtn;
    
    @FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div/div/h6")
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
	
	public void clickZoneBtn() {
		this.promotionZoneBtn.click();
	}
	
	public String productModal() {
		return this.productModal.getText();
	}
	
	public void selection(String productName) {
		this.productList.click();
		this.productList.sendKeys(productName);
		this.productListJack.click();
		this.zoneList.click();
		this.zoneSelect.click();
	}
	
	public String reqCheck() {
		return this.reqCheck.getText();
	}
	
	public void clickGoBtn() {
		this.goBtn.click();
	}
	
	public String promotionModal() {
		return this.promotionModal.getText();
	}
	
	public void selection1(String promotionPercentage,String startDate,String endDate) {
		this.promotionPercentage.click();
		this.promotionPercentage.sendKeys(promotionPercentage);
		this.startDate.click();
		this.startDate.sendKeys(startDate);
		this.endDate.click();
		this.endDate .sendKeys(endDate);
	}
	
	public void applyPromotionBtn() {
		this.applyPromotionBtn.click();
	}
	
	public String tableModal() {
		return this.tableModal.getText();
	}
}
