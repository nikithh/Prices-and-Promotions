/**
 * @author : Zubin

 * @see : This POM is for Add Product To Store Step 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddProductToStoreRetailerPOM {

	private WebDriver driver;
	public AddProductToStoreRetailerPOM(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="username")
	private WebElement username;
	
	@FindBy(id="password")
	private WebElement password;
	
	@FindBy(id="retailer-login-btn")
	private WebElement loginBtn;
	
	@FindBy(id="add-product-store-btn")
	private WebElement addProductsBtn;
	
	@FindBy(id ="app-header")
	private WebElement header;
	
	@FindBy(xpath ="//*[@id=\"root\"]/div/div/div/div/div[1]/table/thead/tr/th[1]")
	private WebElement clustersModal;
	
	@FindBy(id ="special-add-prods-help")
	private WebElement addProductHeader;
	
	@FindBy(id ="zone")
	private WebElement zone;
	
	@FindBy(xpath ="//*[@id=\"zone\"]/option[2]")
	private WebElement zoneOption;
	
	@FindBy(id ="cluster")
	private WebElement cluster;
	
	@FindBy(xpath ="//*[@id=\"cluster\"]/option[2]")
	private WebElement clusterOption;
	
	@FindBy(id ="store")
	private WebElement store;
	
	@FindBy(xpath ="//*[@id=\"store\"]/option[2]")
	private WebElement storeOption;
	
	@FindBy(id ="category")
	private WebElement category;
	
	@FindBy(xpath ="//*[@id=\"category\"]/option[3]")
	private WebElement categoryOption;
	
	@FindBy(id ="add-prods-store-submit")
	private WebElement addProducts;
	
	@FindBy(xpath ="//*[@id=\"root\"]/div/div/div/div/h4")
	private WebElement productTable;
	
	@FindBy(xpath ="//*[@id=\"root\"]/div/div/div/div/div[2]/form/div/table/tbody/tr/td[1]/label/span[1]/span[1]/input")
	private WebElement productCheckbox;
	
	@FindBy(xpath ="//*[@id=\"root\"]/div/div/div/div/div[2]/form/div/table/tbody/tr/td[6]/div/div/input")
	private WebElement quantityBox;
	
	@FindBy(id ="add-product-submit")
	private WebElement submitProduct;
	
	public void sendUserName(String username) {
		this.username.sendKeys(username);;
	}
	
	public void sendPassword(String password) {
		this.password.sendKeys(password); 
	}
	
	public void clickLoginBtn() {
		this.loginBtn.click(); 
	}
	
	public void addProductsBtn() {
		this.addProductsBtn.click();
	}
	
	public String header() {
		return this.header.getText();
	}
	
	public String clustersModal() {
		return this.clustersModal.getText();
	}
	
	public String addProductHeader() {
		return this.addProductHeader.getText();
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
	
	public void store() {
		this.store.click();
	}
	
	public void storeOption() {
		this.storeOption.click();
	}
	
	public void clickAddProducts() {
		this.addProducts.click();
	}
	
	public void category() {
		this.category.click();
	}
	
	public void categoryOption() {
		this.categoryOption.click();
	}
	
	public String productTable() {
		return this.productTable.getText();
	}
	
	public void productCheckbox() {
		this.productCheckbox.click();
	}
	
	public void quantityBox(String quantity) {
		this.quantityBox.sendKeys(quantity);
	}
	
	public void submitProduct() {
		this.submitProduct.click();
	}
}
