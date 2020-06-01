/**
 * @author : Srinidhi

 * @see : This POM is for Admin login 
 * @since : April 2020
 **/
package com.sapient.pps.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdminLoginPOM { 

private WebDriver driver;
    
    public AdminLoginPOM(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    @FindBy(id="admin-login")
    private WebElement adminLogin;
    
    @FindBy(id="username")
    private WebElement username;
    
    @FindBy(id="password")
    private WebElement password;
    
    @FindBy(id="login-btn")
    private WebElement loginBtn;
    
    @FindBy(id="app-header")
    private WebElement header;
    
    @FindBy(xpath="")
    private WebElement invalidMsg;
    
    public void clickAdminLoginBtn() {
        this.adminLogin.click();
    }
    
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
    
    public String invalidMsg() {
        return this.invalidMsg.getText();
    }
    
}