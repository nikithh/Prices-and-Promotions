/**
 * @author : Yatheesha
 * @see : This POM is for vendor registration Step 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VendorRegPOM {
	private WebDriver driver;
	   
    public VendorRegPOM(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    @FindBy(id="email")
    private WebElement email;
    
    @FindBy(id="password")
    private WebElement password;
    
    @FindBy(id="confirmPassword")
    private WebElement confirmpassword;
    
    @FindBy(id="companyName")
    private WebElement companyname;
    
    @FindBy(id="companyType")
    private WebElement companytype;
    
    @FindBy(id="Checkbox")
    private WebElement checkbox;
    
    @FindBy(xpath="//*[@id=\"root\"]/div/div/div/div/form/div[6]/div[1]/span")
    private WebElement productsold;
    
    @FindBy(id="signupsubmit")
    private WebElement signupbtn;
    
    @FindBy(id="vendor-signup-btn")
    private WebElement regbtn;
    
    @FindBy(id="reg-vendor")
	private WebElement vendorbtn;
    
 
    public void clickVendorBtn() {
		this.vendorbtn.click(); 
	}
    
    public void clickregBtn() {
		this.regbtn.click(); 
	}
    
    public void clicSighUpBtn() {
		this.signupbtn.click(); 
	}
    
    public void sendEmail(String email) {
		this.email.sendKeys(email);
	}
    
    public void sendPassword(String password) {
		this.password.sendKeys(password);
	}
    
    public void sendConfirmPassword(String password) {
		this.confirmpassword.sendKeys(password);
	}
    
    public void sendCompanyName(String companyname) {
		this.companyname.sendKeys(companyname);
	}
    
    public void sendCompanyType(String companytype) {
		this.companytype.sendKeys(companytype);
	}
    
    public void sendProductType(String producttype) {
		this.checkbox.click();
	}
    
    public String getButtonLable() {
		return this.regbtn.getText();
	}
}
