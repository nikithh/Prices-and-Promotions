package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ApprovePromotionsPOM {

	private WebDriver driver;
	public ApprovePromotionsPOM(WebDriver driver) {
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
	
	@FindBy(id="admin-dropdown")
	private WebElement adminDropDown;
	
	@FindBy(id="approve-promotion-btn")
	private WebElement approvePromotionBtn;
	
	@FindBy(id="product-name")
	private WebElement productModal;
	
	@FindBy(id="product-list")
    private WebElement productList;
    
    @FindBy(id="product-list-option-0")
    private WebElement Lactogen;
	
	@FindBy(id="approve-btn")
	private WebElement approveBtn;
	
	@FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/div/div/h4")
	private WebElement approvePromModal;

	@FindBy(xpath="//*[@id=\"row35\"]/td[6]/h6/div/button[1]")
	private WebElement acceptBtn;
	
	@FindBy(xpath="//*[@id=\"row19\"]/td[6]/h6/div/button[2]")
	private WebElement rejectBtn;
	
	public void sendUserName(String username) {
		this.username.sendKeys(username);;
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
	
	public void adminDropdownBtn() {
		this.adminDropDown.click();
	}
	
	public void approvePromBtn() {
		this.approvePromotionBtn.click();
	}
	
	public String productModal() {
		return this.productModal.getText();
	}
	
	public void productSelection(String productName) {
		this.productList.click();
		this.productList.sendKeys(productName);
		this.Lactogen.click();
	}
	
	public void approveBtn() {
		this.approveBtn.click();
	}
	
	public String approvePromModal() {
		return this.approvePromModal.getText();
	}
	
	public void clickAcceptBtn() {
		this.acceptBtn.click();
	}
	
	public void clickRejectBtn() {
		this.rejectBtn.click();
	}
	
}
