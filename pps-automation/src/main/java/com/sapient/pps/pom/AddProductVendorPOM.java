/**
 * @author : Srinidhi

 * @see : This POM is for adding product by vendor  
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddProductVendorPOM {

	private WebDriver driver;

	public AddProductVendorPOM(WebDriver driver) {
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

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/nav/div[2]/div/div/div/ul/a[1]/div")
	private WebElement addProductBtn;

	@FindBy(xpath = "//*[@id=\"add-prods-form\"]/form/div[1]/h1")
	private WebElement addProductModal;

	@FindBy(id = "productName")
	private WebElement productName;

	@FindBy(id = "select-product")
	private WebElement category;

	@FindBy(xpath = "//*[@id=\"menu-productCategory\"]/div[3]/ul/li[1]")
	private WebElement alcohol;

	@FindBy(xpath = "//*[@id=\"menu-productCategory\"]/div[3]/ul/li[2]")
	private WebElement baby;

	@FindBy(id = "initialQuantity")
	private WebElement quantity;

	@FindBy(id = "productBasePrice")
	private WebElement basePrice;

	@FindBy(id = "abv")
	private WebElement abv;

	@FindBy(id = "volume")
	private WebElement volume;

	@FindBy(id = "alc-prod")
	private WebElement uom;

	@FindBy(id = "baby-prod")
	private WebElement uom1;

	@FindBy(xpath = "//*[@id=\"menu-uom\"]/div[3]/ul/li[3]")
	private WebElement ml;

	@FindBy(xpath = "//*[@id=\"menu-uom\"]/div[3]/ul/li[1]")
	private WebElement kgs;

	@FindBy(xpath = "//*[@id=\"add-prods-form\"]/form/div[6]/div")
	private WebElement desc;

	@FindBy(id = "submit-prods")
	private WebElement saveBtn;

	public void clickVendorBtn() {
		this.vendorBtn.click();
	}

	public void sendUserName(String username) {
		this.vendorUsername.clear();
		this.vendorUsername.sendKeys(username);
	}

	public void sendPassword(String password) {
		this.vendorPassword.clear();
		this.vendorPassword.sendKeys(password);
	}

	public void clickLoginBtn() {
		this.loginBtn.click();
	}

	public String vendorModal() {
		return this.vendorModal.getText();
	}

	public void clickAddProduct() {
		this.addProductBtn.click();
	}

	public String addProductModal() {
		return this.addProductModal.getText();
	}

	public void selection1(String productName) {
		this.productName.sendKeys(productName);
	}

	public void category1() {
		this.category.click();
		this.alcohol.click();
	}

	public void category2() {
		this.category.click();
		this.baby.click();
	}

	public void selection2(String quantity, String basePrice) {
		this.quantity.sendKeys(quantity);
		this.basePrice.sendKeys(basePrice);
	}

	public void selection3(String abv, String volume) {
		this.abv.sendKeys(abv);
		this.volume.sendKeys(volume);
		//this.uom.click();
		this.ml.click();
	}

	public void selectUom() {
		this.uom1.click();
		this.kgs.click();
	}

	public void selection4(String desc) {
		this.desc.click();
		this.desc.sendKeys(desc);
	}

	public void clickSaveBtn() {
		this.saveBtn.click();
	}
}