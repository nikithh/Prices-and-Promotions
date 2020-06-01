/**
 * @author : Srinidhi

 * @see : This POM is for cancel effective price change 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CancelPromotionPOM {

	private WebDriver driver;
	   
    public CancelPromotionPOM(WebDriver driver) {
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
    
    @FindBy(id="cancel-promotion-btn")
    private WebElement cpBtn;
    
    @FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div[2]/form/div[1]/h1")
    private WebElement promotionModal;
    
    @FindBy(id="product-list")
    private WebElement productList;
    
    @FindBy(id="product-list-option-0")
    private WebElement productListJack;
    
    @FindBy(id="zone")
    private WebElement zoneList;
    
    @FindBy(xpath="//*[@id=\"zone\"]/option[2]")
    private WebElement zoneSelect;
    
    @FindBy(id="cluster-form-submit")
    private WebElement goBtn;
    
    @FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div/div/h5")
    private WebElement promotionTableModal;
    
    @FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div/div/h6/button")
    private WebElement cancelPromotionBtn;
    
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
	
	public void clickCpBtn() {
		this.cpBtn.click();
	}
	
	public String promotionModal() {
		return this.promotionModal.getText();
	}
	
	public void selection(String productName) {
		this.productList.click();
		this.productList.sendKeys(productName);
		this.productListJack.click();
		this.zoneList.click();
		this.zoneSelect.click();
	}
	
	public void clickGoBtn() {
		this.goBtn.click();
	}
	
	public String promotionTableModal() {
		return this.promotionTableModal.getText();
	}
	
	public void cancelPromotionBtn() {
		this.cancelPromotionBtn.click();
	}
}
